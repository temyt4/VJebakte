package com.server.controllers;

import com.server.domain.CommMessage;
import com.server.domain.Community;
import com.server.domain.User;
import com.server.domain.UserMessage;
import com.server.services.CommunityService;
import com.server.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.HashSet;
import java.util.Set;

@Controller
public class MainController {


    @Autowired
    private UserService userService;

    @Autowired
    private CommunityService communityService;

    @Value("${upload.path}")
    private String uploadPath;

    @GetMapping("")
    public String re(){
        return "redirect:/news";
    }

    @GetMapping("/news")
    public String main(@AuthenticationPrincipal User current, Model model) {
        User user = userService.findById(current.getId());
        Set<UserMessage> userMessages = new HashSet<>();
        Set<User> friends = user.getFriends();
        for (User u : friends) {
            userMessages.addAll(u.getMessages());
        }
        Set<CommMessage> commMessages = new HashSet<>();
        Set<Community> communities = user.getCommunities();
        for (Community community : communities) {
            commMessages.addAll(community.getMessages());
        }
        model.addAttribute("commMessage", commMessages);
        model.addAttribute("userMessage", userMessages);
        model.addAttribute("currentUser", user);
        return "news";
    }

    @GetMapping("/friends")
    public String friends(@AuthenticationPrincipal User current, Model model) {
        User user = userService.findById(current.getId());
        Set<User> friends = user.getFriends();
        model.addAttribute("friends", friends);
        model.addAttribute("currentUser", user);
        return "friends";
    }

    @GetMapping("/communities")
    public String communities(@AuthenticationPrincipal User current, Model model) {
        User user = userService.findById(current.getId());
        Set<Community> communities = user.getCommunities();
        model.addAttribute("communities", communities);
        model.addAttribute("currentUser", user);
        return "communitylist";
    }


    @GetMapping("/communities/{name}")
    public String comm(@PathVariable String name, Model umodel, @AuthenticationPrincipal User current) {
        User user = userService.findById(current.getId());
        Community community = communityService.findByName(name);
        Set<CommMessage> messages = community.getMessages();
        umodel.addAttribute("messages", messages);
        umodel.addAttribute("currentUser", user);
        return "community";
    }


}
