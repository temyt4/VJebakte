package com.server.services;

import com.server.domain.CommMessage;
import com.server.domain.Community;
import com.server.repos.CommMessageRepo;
import com.server.repos.CommRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value = "communityService")
public class CommunityService {

    @Autowired
    private CommRepo commRepo;

    @Autowired
    private CommMessageRepo commMessageRepo;

    public Community findByName(String name) {
        return commRepo.findByName(name);
    }

    public void save(Community community) {
        commRepo.save(community);
    }

    public void addNewMessage(Community community, CommMessage commMessage) {
        CommMessage save = commMessageRepo.save(commMessage);
        community.getMessages().add(save);
        commRepo.save(community);
    }
}
