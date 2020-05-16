package com.server.router;

import com.server.handlers.CommunityHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicate;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class CommunityRouter {

    @Bean
    public RouterFunction<ServerResponse> getCommunities(CommunityHandler communityHandler){
        RequestPredicate getCommunities = GET("/communities");

        RequestPredicate post = POST("/communities");

        RequestPredicate addNewGET = GET("/communities/addNew");

        RequestPredicate addNewPOST = POST("/communities/addNew");

        RequestPredicate community = GET("/communities/{community}");

        RequestPredicate postCommunity = POST("/communities/{community}");

        RequestPredicate addNewCommMessage = POST("/communities/{community}/addMessage");

        RequestPredicate commAction = GET("/communities/{community}/{action}");

        return route(getCommunities, communityHandler::getCommunities)
                .andRoute(addNewGET, communityHandler::addNewGET)
                .andRoute(addNewPOST, communityHandler::addNewPOST)
                .andRoute(community, communityHandler::community)
                .andRoute(postCommunity, communityHandler::community)
                .andRoute(addNewCommMessage, communityHandler::addNewComMessage)
                .andRoute(commAction, communityHandler::commAction)
                .andRoute(post, communityHandler::getCommunities);
    }
}
