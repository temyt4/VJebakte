package com.server.handlers;

import com.server.domain.ChatMessage;
import com.server.domain.Comment;
import com.server.domain.User;
import com.server.domain.UserMessage;
import com.server.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.web.server.csrf.DefaultCsrfToken;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.security.Principal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

import static com.server.controllers.ControllerUtil.setToSortedList;
import static com.server.handlers.MainHandler.DEFAULT_TOKEN;

@Component
public class UserHandler {

    @Autowired
    private UserService userService;

    private Logger logger = LoggerFactory.getLogger(UserHandler.class);

    public static Map<String, Object> addToken(ServerRequest serverRequest){
        HashMap<String, Object> map = new HashMap<>();
        Mono<DefaultCsrfToken> csrfToken = serverRequest.exchange().getAttribute(DEFAULT_TOKEN);
        csrfToken.subscribe(t->map.put("_csrf", t));
        return map;
    }

    private Mono<ServerResponse> userPageDefault(ServerRequest serverRequest){
        Map<String, Object> map = addToken(serverRequest);
        String username = serverRequest.pathVariables().get("username");
        Mono<? extends Principal> principalMono = serverRequest.principal();
        return userService.findByUserName(username).flatMap(user->{
            if(user==null) {
                logger.info("user not exists");
                return ServerResponse.badRequest().build();
            }

            return defaultMapUserPage(principalMono
                    .flatMap(principal->userService.findByUserName(principal.getName())), user, map);
        });
    }

    private Mono<ServerResponse> defaultMapUserPage(Mono<User> currentMono,User user, Map<String, Object> map){
        return currentMono.flatMap(currentUser->{
            map.put("user", user);
            map.put("currentUser", currentUser);
            map.put("isCurrentUserPage", user.equals(currentUser));
            map.put("messages", user.getMessages());
            logger.info(user.getBirthdate().toString());
            map.put("birthdate", user.getBirthdate().toString());
            map.put("isFriend", currentUser.getFriends().contains(user.getUsername()));
            map.put("way", "users"+user.getUsername());
            return userService.getUserMessages(user).flatMap(userMessages -> {
//                ArrayList<UserMessage> messages = new ArrayList<>(user.getMessages());
//                messages.sort((o1, o2) -> o2.getCreatedDate().compareTo(o1.getCreatedDate()));
//                for (UserMessage message : messages) {
//                    logger.info(message.getComments().size()+"");
//                    ArrayList<Comment> comments = new ArrayList<>(message.getComments());
//                    comments.sort((o1, o2) -> o2.getCreatedDate().compareTo(o1.getCreatedDate()));
//                    Set<Comment> collect = new HashSet<>(comments);
//                    message.setComments(collect);
//                }
                map.put("messages", userMessages);
                return ServerResponse.ok().render("userpage", map);
            });
        });
    }

    private Mono<ServerResponse> userPageAddText(ServerRequest serverRequest, String text) {
        Map<String, Object> map = addToken(serverRequest);
        String username = serverRequest.pathVariables().get("username");
        Mono<? extends Principal> principalMono = serverRequest.principal();
        return userService.findByUserName(username).flatMap(user -> {
            if (user == null) {
                logger.info("user not exists");
                return ServerResponse.badRequest().build();
            }

            return principalMono
                    .flatMap(principal -> userService.findByUserName(principal.getName()).flatMap(currentUser -> {
                        UserMessage message = new UserMessage();
                        message.setAuthorName(currentUser.getUsername());
                        message.setText(text);
                        message.setCreatedDate(new Date());
                        message.setId(UUID.randomUUID().toString());
                        return userService.addNewMessage(currentUser, message);
                    }).flatMap(cUser->defaultMapUserPage(Mono.just(cUser), cUser, map)
                    ));
        });
    }

    public Mono<ServerResponse> postUserPage(ServerRequest serverRequest){
        Mono<MultiValueMap<String, String>> formData = serverRequest.formData();
        return formData.flatMap(data->{
            String text = data.getFirst("textMes");
            if(text==null){
                return userPageDefault(serverRequest);
            } else {
                return userPageAddText(serverRequest, text);
            }
        });
    }


    public Mono<ServerResponse> getUserPage(ServerRequest serverRequest){
        return userPageDefault(serverRequest);
    }

    public Mono<ServerResponse> userAction(ServerRequest serverRequest){
        String action = serverRequest.pathVariable("action");
        String username = serverRequest.pathVariable("username");
        logger.info(action);
        Mono<? extends Principal> principalMono = serverRequest.principal();
        return principalMono.flatMap(principal->userService.findByUserName(principal.getName())
                .flatMap(currentUser->userService.findByUserName(username).flatMap(user->{
                    if(action.equals("add")){
                        logger.info(user.getUsername()+" and "+currentUser.getUsername()+" are friends now");
                        userService.addFriend(user, currentUser);
                    } else if(action.equals("delete")){
                        logger.info(user.getUsername()+" and "+currentUser.getUsername()+" are not friends now");
                        userService.deleteFriend(user, currentUser);
                    }
                    return ServerResponse.permanentRedirect(URI.create("/users/"+user.getUsername())).build();
                })));
    }

    public Mono<ServerResponse> getChats(ServerRequest serverRequest){
        Map<String, Object> map = addToken(serverRequest);
        return serverRequest.principal().flatMap(principal->userService.findByUserName(principal.getName()).flatMap(currentUser->{
            map.put("currentUser", currentUser);
            map.put("friends", currentUser.getFriends());
            map.put("user", currentUser);
            return ServerResponse.ok().render("usersForChat", map);
        }));
    }

    public Mono<ServerResponse> getUserChat(ServerRequest serverRequest){
        Map<String, Object> map = addToken(serverRequest);
        String name = serverRequest.pathVariables().get("name");
        return serverRequest.principal().flatMap(principal->userService.findByUserName(principal.getName()).flatMap(currentUser->{
            if(currentUser.getUsername().equals(name))
                return ServerResponse.temporaryRedirect(URI.create("/users")).build();
            else {
                return userService.findByUserName(name).flatMap(friend->{
                    List<ChatMessage> messages = setToSortedList(currentUser.getChatMessages(), friend);
                    messages.addAll(setToSortedList(friend.getChatMessages(), currentUser));
                    messages.sort(Comparator.comparing(ChatMessage::getCreatedDate));
                    map.put("currentUser", currentUser);
                    map.put("user", friend);
                    map.put("messages", messages);
                    return ServerResponse.ok().render("chat", map);
                });
            }
        }));
    }

    public Mono<ServerResponse> saveChatMessage(ServerRequest serverRequest){
        Map<String, Object> map = addToken(serverRequest);
        String name = serverRequest.pathVariables().get("name");

        return serverRequest.formData().flatMap(data->{
            String text = data.getFirst("text");
            logger.info("rabotaet");
            return  serverRequest.principal().flatMap(principal->userService.findByUserName(principal.getName()).flatMap(currentUser->{
                return userService.findByUserName(name).flatMap(friend->{
                    ChatMessage chatMessage = new ChatMessage();
                    chatMessage.setFriendName(friend.getUsername());
                    chatMessage.setFriendId(friend.getId());
                    chatMessage.setAuthorName(currentUser.getUsername());
                    chatMessage.setAuthorId(currentUser.getId());
                    chatMessage.setId(UUID.randomUUID().toString());
                    chatMessage.setText(text);
                    LocalDateTime localDateTime = LocalDateTime.now();
                    chatMessage.setCreatedDate(Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant()));
                    logger.info("still rabotaet");
                    return userService.saveChatMessage(currentUser, chatMessage).flatMap(user->{
                        logger.info("a tut pizda");
                        List<ChatMessage> messages = setToSortedList(currentUser.getChatMessages(), friend);
                        messages.addAll(setToSortedList(friend.getChatMessages(), currentUser));
                        messages.sort(Comparator.comparing(ChatMessage::getCreatedDate));
                        map.put("currentUser", currentUser);
                        map.put("user", friend);
                        map.put("messages", messages);
                        return ServerResponse.ok().render("chat", map);
                    });
                });
            }));
        });

    }


}
