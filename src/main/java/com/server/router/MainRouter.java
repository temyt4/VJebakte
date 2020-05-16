package com.server.router;

import com.server.handlers.MainHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicate;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class MainRouter {

    @Bean
    public RouterFunction<ServerResponse> mainRoute(MainHandler mainHandler){

        RequestPredicate get = GET("/news");

        RequestPredicate post = POST("/news");

        RequestPredicate login = GET("/login");

        RequestPredicate friends = GET("/friends");

        RequestPredicate registrationGet = GET("/registration");

        RequestPredicate registrationPost = POST("/registration");

        RequestPredicate addComment = POST("/{uni}/addComment/{way}");

        RequestPredicate like = GET("/{where}/{uni}/{action}/{way}");


        return route(get, mainHandler::news)
                .andRoute(login, mainHandler::login)
                .andRoute(friends, mainHandler::friends)
                .andRoute(registrationGet, mainHandler::registrationGet)
                .andRoute(registrationPost, mainHandler::registrationPost)
                .andRoute(addComment, mainHandler::addComment)
                .andRoute(post, mainHandler::news)
                .andRoute(like, mainHandler::like);
    }

}
