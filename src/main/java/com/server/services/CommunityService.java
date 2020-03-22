package com.server.services;

import com.server.domain.Community;
import com.server.repos.CommRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value = "communityService")
public class CommunityService {

    @Autowired
    private CommRepo commRepo;

    public Community findByName(String name){
        return commRepo.findByName(name);
    }
}
