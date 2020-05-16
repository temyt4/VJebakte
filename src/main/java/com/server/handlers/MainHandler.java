package com.server.handlers;

import com.server.domain.User;
import com.server.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.csrf.DefaultCsrfToken;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.security.Principal;
import java.util.*;

import static com.server.handlers.UserHandler.addToken;

@Component
public class MainHandler {

    private Logger logger = LoggerFactory.getLogger(MainHandler.class);
    public final static String DEFAULT_TOKEN = "org.springframework.security.web.server.csrf.CsrfToken";

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private String finalWay(String way){
        if(way.startsWith("users")) {
            String begin = way.substring(0, 5);
            logger.info(begin);
            String end = way.substring(5);
            way = begin+"/"+end;
            logger.info(way);
        } else if(way.startsWith("communities")){
            String begin = way.substring(0, 11);
            logger.info(begin);
            String end = way.substring(11);
            way = begin+"/"+end;
        }
        return way;
    }

    public Mono<ServerResponse> news(ServerRequest serverRequest) {
        Map<String, Object> map = addToken(serverRequest);

        return serverRequest.principal().flatMap(p -> {

            logger.info(p.getName());

            return userService.findByUserName(p.getName()).flatMap(user -> {
                map.put("user", user);
                map.put("currentUser", user);
                return userService.getMessagesForNews(user).flatMap(messages -> {
                    map.put("messages", messages);
                    map.put("way", "news");
                    return ServerResponse.ok().render("news", map);
                });
            });

        });
    }

    public Mono<ServerResponse> login(ServerRequest serverRequest){
        Mono<DefaultCsrfToken> csrfToken = serverRequest.exchange().getAttribute(DEFAULT_TOKEN);
        HashMap<String, Object> map = new HashMap<>();
        csrfToken.subscribe(t->map.put("_csrf", t));
        return ServerResponse.ok().render("login", map);
    }


    public Mono<ServerResponse> friends(ServerRequest serverRequest){
        Map<String, Object> map = addToken(serverRequest);
        Mono<? extends Principal> principal = serverRequest.principal();
        return principal.flatMap(user->userService.findByUserName(user.getName()).flatMap(currentUser->{
            map.put("friends", currentUser.getFriends());
            map.put("currentUser", currentUser);
            map.put("user", currentUser);
            return ServerResponse.ok().render("friends", map);
        }));
    }

    public Mono<ServerResponse> registrationGet(ServerRequest serverRequest){
        Map<String, Object> map = addToken(serverRequest);
        return ServerResponse.ok().render("registration", map);
    }

    public Mono<ServerResponse> registrationPost(ServerRequest serverRequest){
        return serverRequest.formData().flatMap(map->{
            String username = map.getFirst("username");
            String password = map.getFirst("password");
            String birthdate = map.getFirst("birthdate");
            return userService.findByUserName(username).hasElement().flatMap(f->{
                if(f) {
                    logger.info("user from db exists");
                    return ServerResponse.badRequest().build();
                } else {
                    logger.info("creating new user");
                    User user = new User();
                    user.setId(UUID.randomUUID().toString());
                    user.setUsername(username);
                    user.setPassword(passwordEncoder.encode(password));
                    user.setUser_avatar("default.jpg");
                    String[] strings = birthdate.split("-");
                    user.setBirthdate(new Date(new GregorianCalendar(Integer.parseInt(strings[0]), Integer.parseInt(strings[1]), Integer.parseInt(strings[2])).getTime().getTime()));
                    userService.save(user).subscribe(u->{
                        logger.info("user successfully created");
                    });
                    return ServerResponse.temporaryRedirect(URI.create("/login")).build();
                }
            });
        });
    }

    public Mono<ServerResponse> addComment(ServerRequest serverRequest){
        String uni = serverRequest.pathVariables().get("uni");
        logger.info(serverRequest.path());
        logger.info(serverRequest.uri().toString());
        String finalWay = finalWay(serverRequest.pathVariable("way"));
        return serverRequest.formData().flatMap(data->{
            String text = data.getFirst("text");
            return serverRequest.principal().flatMap(principal->userService.findByUserName(principal.getName())
                    .flatMap(currentUser->{
                            userService.addNewComment(uni, text, null, currentUser);
                            return ServerResponse.temporaryRedirect(URI.create("/"+ finalWay)).build();
                    }));
        });
    }

    public Mono<ServerResponse> like(ServerRequest serverRequest){
        String uni = serverRequest.pathVariables().get("uni");
        String where = serverRequest.pathVariables().get("where");
        String action = serverRequest.pathVariables().get("action");

        String finalWay = finalWay(serverRequest.pathVariable("way"));


        return serverRequest.principal().flatMap(principal->{
            return userService.findByUserName(principal.getName()).flatMap(currentUser->{
                if(action.equals("like")){
                    return userService.likeMessage(uni, currentUser, where).flatMap(message -> {
                        return ServerResponse.temporaryRedirect(URI.create("/"+finalWay)).build();
                    });
                } else if(action.equals("unLike")){
                    return userService.unLikeMessage(uni, currentUser, where).flatMap(message -> {
                        return ServerResponse.temporaryRedirect(URI.create("/"+finalWay)).build();
                    });
                } else return ServerResponse.badRequest().build();
            });
        });

    }

}
