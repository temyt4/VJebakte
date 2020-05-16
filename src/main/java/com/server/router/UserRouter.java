package com.server.router;

import com.server.handlers.UserHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicate;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class UserRouter {

    @Bean
    public RouterFunction<ServerResponse> userPageRouter(UserHandler userHandler){

        RequestPredicate getUserPage = GET("/users/{username}");

        RequestPredicate postUserPage = POST("/users/{username}");

        RequestPredicate userPageAction = POST("/users/{username}/{action}");

        RequestPredicate usersForChat = GET("/users");

        RequestPredicate getChat = GET("/users/getChat/{name}");

        RequestPredicate post = POST("/users/getChat/{name}");

        RequestPredicate addChatMessage = POST("/users/chat/addChat/{name}");

        return route(getUserPage, userHandler::getUserPage)
                .andRoute(userPageAction, userHandler::userAction)
                .andRoute(postUserPage, userHandler::postUserPage)
                .andRoute(usersForChat, userHandler::getChats)
                .andRoute(getChat, userHandler::getUserChat)
                .andRoute(addChatMessage, userHandler::saveChatMessage)
                .andRoute(post, userHandler::getUserChat);
    }
}
