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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

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
    public String main(@AuthenticationPrincipal User user, Model model){
        Set<UserMessage> userMessages = new HashSet<>();
        Set<User> friends = user.getFriends();
        for(User u: friends){
            userMessages.addAll(u.getMessages());
        }
        Set<CommMessage> commMessages = new HashSet<>();
        Set<Community> communities = user.getCommunities();
        for(Community community:communities){
            commMessages.addAll(community.getMessages());
        }
        model.addAttribute("commMessage", commMessages);
        model.addAttribute("userMessage", userMessages);
        return "news";
    }

    @GetMapping("/friends")
    public String friends(@AuthenticationPrincipal User user, Model model){
        Set<User> friends = user.getFriends();
        model.addAttribute("friends", friends);

        return "friends";
    }

    @GetMapping("/communities")
    public String communities(@AuthenticationPrincipal User user, Model model){
        Set<Community> communities = user.getCommunities();
        model.addAttribute("communities", communities);
        return "communitylist";
    }

    @GetMapping("/users/{username}")
    public String userPage(@PathVariable String username, Model model, @AuthenticationPrincipal User currentUser) {
        User user = userService.findByUserName(username);
        model.addAttribute("isCurrentUserPage", currentUser.equals(user));
        model.addAttribute("user", user);
        Set<UserMessage> messages = user.getMessages();
        model.addAttribute("messages", messages);
        return "userpage";
    }

    @GetMapping("/communities/{name}")
    public String comm(@PathVariable String name, Model model, @AuthenticationPrincipal User currentUser){
        Community community = communityService.findByName(name);
        Set<CommMessage> messages = community.getMessages();
        model.addAttribute("messages", messages);
        return "community";
    }

    @PostMapping("/users/{name}")
    public String addMessage(@PathVariable String name, Model model, @AuthenticationPrincipal User currentUser, @RequestParam String text){
        User user = userService.findByUserName(name);
        UserMessage userMessage = new UserMessage();
        LocalDateTime localDateTime = LocalDateTime.now();
        userMessage.setCreatedDate(Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant()));
        userMessage.setAuthorId(user.getId());
        userMessage.setText(text);
        userMessage.setAuthorName(user.getUsername());
        userService.addNewMessage(user, userMessage);
        return "redirect:/users/" + name;
    }

    @GetMapping("/users/{name}/edit")
    public String edit(@PathVariable String name,
                       @AuthenticationPrincipal User currentUser,
                       Model model){
        User user = userService.findByUserName(name);
        model.addAttribute("user", user);
        return "userEdit";
    }

    @PostMapping("/users/{name}/edit")
    public String userEdit(@PathVariable String name,
                           @AuthenticationPrincipal User currentUser,
                           Model model,
                           @RequestParam String username,
                           @RequestParam String password,
                           @RequestParam String birthdate,
                           @RequestParam String email,
                           @RequestParam("avatar") MultipartFile avatar) throws IOException {
        User user = userService.findByUserName(name);

        if(username!=null && !username.isEmpty()){
            user.setUsername(username);
        }

        if(password!=null && !password.isEmpty()){
            userService.setPassword(user, password);
        }

        editAvatar(user, avatar);

        if(email!=null && !email.isEmpty()){
            user.setEmail(email);
        }

        userService.save(user);


        return "redirect:/users/" + user.getUsername();
    }

    private void editAvatar(User user, @RequestParam MultipartFile file) throws IOException {
        if (file != null && !file.getOriginalFilename().isEmpty()) {
            File uploadDir = new File(uploadPath);

            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            String uuIDFile = UUID.randomUUID().toString();
            String resultFileName = uuIDFile + "." + file.getOriginalFilename();

            file.transferTo(new File(uploadPath + "/" + resultFileName));

            user.setUser_avatar(resultFileName);

        }
    }

}
