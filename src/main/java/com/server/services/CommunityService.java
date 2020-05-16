package com.server.services;

import com.server.domain.CommMessage;
import com.server.domain.Community;
import com.server.domain.Message;
import com.server.domain.User;
import com.server.repos.CommMessageRepo;
import com.server.repos.CommRepo;
import com.server.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;


/**
 * created by xev11
 */

@Service(value = "communityService")
public class CommunityService {

    @Autowired
    private CommRepo commRepo;

    @Autowired
    private CommMessageRepo commMessageRepo;

    @Autowired
    private UserRepo userRepo;

    public Mono<Community> findByName(String name) {
        return commRepo.findByName(name);
    }

    public Mono<Community> save(Community community) {
        return commRepo.save(community);
    }


    public Mono<Community> addNewMessage(Community community, CommMessage commMessage) {
        return commMessageRepo.save(commMessage).flatMap(message->{
            community.getMessages().add(message);
            return save(community);
        });
    }

    public Mono<Tuple2<Community, User>> commAction(User currentUser, Community community, String action){
        if(action.equals("sub")){
            community.getCommunity_users().add(currentUser.getUsername());
            currentUser.getCommunities().add(community.getName());
        } else if(action.equals("unsub")){
            community.getCommunity_users().remove(currentUser.getUsername());
            currentUser.getCommunities().remove(community.getName());
        }

        return commRepo.save(community).zipWith(userRepo.save(currentUser));
    }

    public Flux<Community> findAll(){
        return commRepo.findAll();
    }

    public Flux<CommMessage> findCommMessages() {
        return commMessageRepo.findAll();
    }

    public Flux<Message> findMessageByName(String name) {
        return commMessageRepo.findByAuthorName(name).map(m->m);
    }



}
