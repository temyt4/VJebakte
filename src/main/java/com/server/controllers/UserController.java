package com.server.controllers;

import com.server.domain.User;
import com.server.domain.UserMessage;
import com.server.domain.dto.UserDto;
import com.server.services.CommunityService;
import com.server.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

@RequestMapping("/users")
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private CommunityService communityService;

    @Value("${upload.path}")
    private String uploadPath;

    @GetMapping("{username}")
    public String userPage(@PathVariable String username, Model model, @AuthenticationPrincipal User current) {
        UserDto currentUser = userService.findUserDtoById(current.getId());
        UserDto user = userService.findUserDtoByUsername(username);
        model.addAttribute("isCurrentUserPage", currentUser.equals(user));
        model.addAttribute("user", user);
        Set<UserMessage> messages = user.getMessages();
        model.addAttribute("messages", messages);
        if (!currentUser.equals(user)) {
            model.addAttribute("isFriend", currentUser.getFriends().contains(userService.findByUserName(username)));
        }
        model.addAttribute("currentUser", currentUser);
        return "userpage";
    }

    @PostMapping("{name}")
    public String addMessage(@PathVariable String name, @RequestParam String text, @AuthenticationPrincipal User current, Model model) {
        User currentUser = userService.findById(current.getId());
        User user = userService.findByUserName(name);
        UserMessage userMessage = new UserMessage();
        LocalDateTime localDateTime = LocalDateTime.now();
        userMessage.setCreatedDate(Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant()));
        userMessage.setAuthorId(user.getId());
        userMessage.setText(text);
        userMessage.setAuthorName(user.getUsername());
        userService.addNewMessage(user, userMessage);
        model.addAttribute("currentUser", currentUser);
        return "redirect:/users/" + name;
    }

    @GetMapping("{name}/edit")
    public String edit(@PathVariable String name,
                       Model model,
                       @AuthenticationPrincipal User current) {
        User currentUser = userService.findById(current.getId());
        UserDto user = userService.findUserDtoByUsername(name);
        model.addAttribute("user", user);
        model.addAttribute("currentUser", currentUser);
        return "userEdit";
    }

    @PostMapping("{name}/edit")
    public String userEdit(@PathVariable String name,
                           Model model,
                           @RequestParam String username,
                           @RequestParam String password,
                           @RequestParam String birthdate,
                           @RequestParam String email,
                           @RequestParam("avatar") MultipartFile avatar,
                           @AuthenticationPrincipal User current) throws IOException {
        User currentUser = userService.findById(current.getId());
        User user = userService.findByUserName(name);

        if (username != null && !username.isEmpty()) {
            user.setUsername(username);
            Set<UserMessage> messages = user.getMessages();
            for (UserMessage message : messages) {
                message.setAuthorName(username);
                userService.saveMessage(message);
            }
        }

        if (password != null && !password.isEmpty()) {
            userService.setPassword(user, password);
        }

        editAvatar(user, avatar);

        if (email != null && !email.isEmpty()) {
            user.setEmail(email);
        }


        userService.save(user);

        model.addAttribute("currentUser", currentUser);

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

    @PostMapping("{username}/{action}")
    public String addOrDelete(@PathVariable String username, @PathVariable String action, @AuthenticationPrincipal User current, Model model) {
        User currentUser = userService.findById(current.getId());
        User user = userService.findByUserName(username);
        if (action.equals("add")) {
            userService.addFriend(user, currentUser);
        } else if (action.equals("delete")) {
            userService.deleteFriend(user, currentUser);
        }

        model.addAttribute("currentUser", currentUser);
        return "redirect:/users/" + username;
    }

}
