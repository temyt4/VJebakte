package com.server.controllers;

import com.server.domain.User;
import com.server.domain.dto.MessageDto;
import com.server.services.CommunityService;
import com.server.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Set;

/**
 * created by xev11
 */

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
    public String main(@AuthenticationPrincipal User current,
                       Model model) {
        User user = userService.findById(current.getId());
        List<MessageDto> messages = ControllerUtil.getAllMessages(user, userService, communityService);
        model.addAttribute("messages", messages);
        model.addAttribute("currentUser", user);
        return "news";

    }

    @GetMapping("/friends")
    public String friends(@AuthenticationPrincipal User current,
                          Model model) {
        User user = userService.findById(current.getId());
        Set<User> friends = user.getFriends();
        model.addAttribute("friends", friends);
        model.addAttribute("currentUser", user);
        return "friends";
    }


}
