package com.server.handlers;

import com.server.domain.CommMessage;
import com.server.domain.Community;
import com.server.services.CommunityService;
import com.server.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.*;

import static com.server.handlers.UserHandler.addToken;

@Component
public class CommunityHandler {

    private Logger logger = LoggerFactory.getLogger(CommunityHandler.class);

    @Autowired
    private UserService userService;

    @Autowired
    private CommunityService communityService;

    public Mono<ServerResponse> getCommunities(ServerRequest serverRequest) {
        Map<String, Object> map = addToken(serverRequest);
        return serverRequest.principal().flatMap(principal -> {
            return userService.findByUserName(principal.getName()).flatMap(currentUser -> {
                return communityService.findAll().filter(community -> currentUser.getCommunities().contains(community.getName())).collectList().flatMap(list -> {
                    map.put("currentUser", currentUser);
                    map.put("user", currentUser);
                    map.put("communities", list);
                    return ServerResponse.ok().render("communitylist", map);
                });
            });
        });
    }

    public Mono<ServerResponse> addNewGET(ServerRequest serverRequest) {
        Map<String, Object> map = addToken(serverRequest);
        return serverRequest.principal().flatMap(principal -> {
            return userService.findByUserName(principal.getName()).flatMap(currentUser -> {
                map.put("currentUser", currentUser);
                map.put("user", currentUser);
                return ServerResponse.ok().render("newCommunity", map);
            });
        });
    }

    public Mono<ServerResponse> addNewPOST(ServerRequest serverRequest) {

        return serverRequest.formData().flatMap(data->{
            String name = data.getFirst("name");
            Community community = new Community();
            return serverRequest.principal().flatMap(principal->userService.findByUserName(principal.getName()).flatMap(currentUser->{
                community.setId(UUID.randomUUID().toString());
                community.setAvatar("default.jpg");
                community.setName(name);
                community.setAdmins(Collections.singleton(currentUser.getUsername()));
                community.setCommunity_users(Collections.singleton(currentUser.getUsername()));
                return communityService.save(community).flatMap(c->{
                    currentUser.getCommunities().add(c.getName());
                    return userService.save(currentUser).flatMap(user->{
                        return ServerResponse.temporaryRedirect(URI.create("/communities/"+name)).build();
                    });
                });

            }));
        });

    }

    public Mono<ServerResponse> community(ServerRequest serverRequest) {
        String communityName = serverRequest.pathVariables().get("community");
        Map<String, Object> map = addToken(serverRequest);
        return serverRequest.principal().flatMap(principal -> {
            return userService.findByUserName(principal.getName()).flatMap(currentUser -> {
                return communityService.findByName(communityName).flatMap(community -> {
                    map.put("currentUser", currentUser);
                    map.put("user", currentUser);
                    map.put("comm", community);
                    map.put("isCommAdmin", community.getAdmins().contains(currentUser.getUsername()));
                    map.put("subs", community.getCommunity_users().size());
                    map.put("isSub", community.getCommunity_users().contains(currentUser.getUsername()));
                    map.put("way", "communities"+community.getName());
                    return communityService.findCommMessages().filter(commMessage -> commMessage.getAuthorName().equals(community.getName())).collectList().flatMap(list->{
                        map.put("messages", list);
                        return ServerResponse.ok().render("community", map);
                    });
                });
            });
        });
    }

    public Mono<ServerResponse> addNewComMessage(ServerRequest serverRequest) {
        String communityName = serverRequest.pathVariables().get("community");
        return serverRequest.formData().flatMap(data -> {
            String text = data.getFirst("text");
            return serverRequest.principal().flatMap(principal -> {
                return userService.findByUserName(principal.getName()).flatMap(currentUser -> {
                    return communityService.findByName(communityName).flatMap(community -> {
                        CommMessage commMessage = new CommMessage();
                        commMessage.setAuthorName(community.getName());
                        commMessage.setText(text);
                        commMessage.setId(UUID.randomUUID().toString());
                        commMessage.setCreatedDate(new Date());
                        return communityService.addNewMessage(community, commMessage).flatMap(c -> {
                            return ServerResponse.temporaryRedirect(URI.create("/communities/" + c.getName())).build();
                        });
                    });
                });
            });
        });
    }

    public Mono<ServerResponse> commAction(ServerRequest serverRequest){
        String communityName = serverRequest.pathVariables().get("community");
        String action = serverRequest.pathVariables().get("action");
        return serverRequest.principal().flatMap(principal->{
            return userService.findByUserName(principal.getName()).flatMap(currentUser->{
                return communityService.findByName(communityName).flatMap(community -> {
                    return communityService.commAction(currentUser, community, action).flatMap(t2->{
                        return ServerResponse.temporaryRedirect(URI.create("/communities/"+communityName)).build();
                    });
                });
            });
        });
    }
}
