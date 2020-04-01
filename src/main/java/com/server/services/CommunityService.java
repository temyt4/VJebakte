package com.server.services;

import com.server.domain.CommMessage;
import com.server.domain.Community;
import com.server.domain.dto.MessageDto;
import com.server.repos.CommMessageRepo;
import com.server.repos.CommRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * created by xev11
 */

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

    public Set<MessageDto> findMessageDtoByName(String name) {
        return commMessageRepo.findDtoByAuthorName(name);
    }

    public List<CommMessage> findCommMessages() {
        return commMessageRepo.findAll();
    }
}
