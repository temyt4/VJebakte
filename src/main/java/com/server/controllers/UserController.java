package com.server.controllers;

import com.server.domain.ChatMessage;
import com.server.domain.Comment;
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

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

/**
 * created by xev11
 */

@RequestMapping("/users")
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private CommunityService communityService;

    @Value("${upload.path}")
    private String uploadPath;

    @GetMapping("")
    public String users(@AuthenticationPrincipal User current,
                        Model model) {
        User user = userService.findById(current.getId());
        Set<User> friends = user.getFriends();
        model.addAttribute("friends", friends);
        model.addAttribute("currentUser", user);
        return "usersForChat";

    }

    @GetMapping("{username}")
    public String userPage(@PathVariable String username,
                           Model model,
                           @AuthenticationPrincipal User current) {
        User currentUser = userService.findById(current.getId());
        User user = userService.findByUserName(username);
        model.addAttribute("isCurrentUserPage", currentUser.equals(user));
        model.addAttribute("user", user);
        ArrayList<UserMessage> messages = new ArrayList<>(user.getMessages());
        messages.sort((o1, o2) -> o2.getCreatedDate().compareTo(o1.getCreatedDate()));
        for (UserMessage message : messages) {
            Set<Comment> comments = message.getComments().stream().sorted(Comparator.comparing(Comment::getCreatedDate)).collect(Collectors.toCollection(LinkedHashSet::new));
            message.setComments(comments);
        }
        model.addAttribute("messages", messages);
        if (!currentUser.equals(user)) {
            model.addAttribute("isFriend", currentUser.getFriends().contains(user));
        }
        model.addAttribute("currentUser", currentUser);
        return "userpage";
    }

    @PostMapping("{name}")
    public String addMessage(@PathVariable String name,
                             @RequestParam String text,
                             @AuthenticationPrincipal User current,
                             Model model) {
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

        ControllerUtil.editAvatar(user, avatar, uploadPath);

        if (email != null && !email.isEmpty()) {
            user.setEmail(email);
        }


        userService.save(user);

        model.addAttribute("currentUser", currentUser);

        return "redirect:/users/" + user.getUsername();
    }


    @PostMapping("{username}/{action}")
    public String addOrDelete(@PathVariable String username,
                              @PathVariable String action,
                              @AuthenticationPrincipal User current,
                              Model model) {
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

    @GetMapping("{name}/chat")
    public String chat(@PathVariable String name,
                       @AuthenticationPrincipal User current,
                       Model model) {
        User user = userService.findByUserName(name);
        Long id = user.getId();
        User currentUser = userService.findById(current.getId());

        if (user.equals(currentUser)) {
            return "redirect:/users/" + name;
        }

        List<ChatMessage> messages = ControllerUtil.setToSortedList(currentUser.getChatMessages(), user);

        messages.addAll(ControllerUtil.setToSortedList(user.getChatMessages(), currentUser));

        messages.sort(Comparator.comparing(ChatMessage::getCreatedDate));

        model.addAttribute("currentUser", currentUser);
        model.addAttribute("messages", messages);

        return "chat";
    }

    @PostMapping("{name}/chat")
    public String editChat(@PathVariable String name,
                           @AuthenticationPrincipal User current,
                           Model model,
                           @RequestParam String text,
                           @RequestParam MultipartFile file) throws IOException {

        User user = userService.findByUserName(name);
        User currentUser = userService.findById(current.getId());

        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setAuthorId(currentUser.getId());
        chatMessage.setAuthorName(currentUser.getUsername());
        LocalDateTime localDateTime = LocalDateTime.now();
        chatMessage.setCreatedDate(Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant()));
        ControllerUtil.setFile(chatMessage, file, uploadPath);
        chatMessage.setFriendId(user.getId());
        chatMessage.setFriendName(user.getUsername());
        chatMessage.setText(text);

        userService.saveChatMessage(currentUser, chatMessage);

        return "redirect:/users/" + name + "/chat";

    }


}
