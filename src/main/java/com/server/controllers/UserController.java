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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

/**
 * created by xev11
 */

//@RequestMapping("/users")
//@Controller
//public class UserController {
//
//    @Autowired
//    private UserService userService;
//
//    @Autowired
//    private CommunityService communityService;
//
//    @Value("${upload.path}")
//    private String uploadPath;
//
//    @GetMapping("")
//    public String users(@AuthenticationPrincipal User current,
//                        Model model) {
//        Mono<User> user = userService.findById(current.getId());
//        user.subscribe(u->{
//            model.addAttribute("friends", u.getFriends());
//            model.addAttribute("currentUser", u);
//        });
//
//        return "usersForChat";
//
//    }
//
////    @GetMapping("{username}")
////    public String userPage(@PathVariable String username,
////                           Model model,
////                           @AuthenticationPrincipal UserDetails current) {
////        Mono<User> currentUser = userService.findByUserName(current.getUsername());
////        Mono<User> user = userService.findByUserName(username);
////        model.addAttribute("isCurrentUserPage", currentUser.equals(user));
////
////        user.subscribe(u->{
////            model.addAttribute("user", u);
////            ArrayList<UserMessage> messages = new ArrayList<>(u.getMessages());
////            messages.sort((o1, o2) -> o2.getCreatedDate().compareTo(o1.getCreatedDate()));
////            for (UserMessage message : messages) {
////                Set<Comment> comments = message.getComments().stream().sorted(Comparator.comparing(Comment::getCreatedDate)).collect(Collectors.toCollection(LinkedHashSet::new));
////                message.setComments(comments);
//            }
//            model.addAttribute("messages", messages);
//            currentUser.subscribe(c->{
//                if (!c.equals(u)) {
//                    model.addAttribute("isFriend", c.getFriends().contains(u));
//                }
//                model.addAttribute("currentUser", c);
//            });
//
//        });
//
//
//        return "userpage";
//    }
//
//    @PostMapping("{name}")
//    public String addMessage(@PathVariable String name,
//                             @RequestParam String text,
//                             @AuthenticationPrincipal User current,
//                             Model model) {
//        Mono<User> currentUser = userService.findById(current.getId());
//        Mono<User> user = userService.findByUserName(name);
//
//        user.subscribe(u->{
//            UserMessage userMessage = new UserMessage();
//            LocalDateTime localDateTime = LocalDateTime.now();
//            userMessage.setCreatedDate(Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant()));
//
//            userMessage.setAuthorId(u.getId());
//            userMessage.setText(text);
//            userMessage.setAuthorName(u.getUsername());
//            userService.addNewMessage(user, userMessage);
//            model.addAttribute("currentUser", u);
//        });
//
//        return "redirect:/users/" + name;
//    }

//    @GetMapping("{name}/edit")
//    public String edit(@PathVariable String name,
//                       Model model,
//                       @AuthenticationPrincipal User current) {
//        Mono<User> currentUser = userService.findById(current.getId());
//        Mono<User> user = userService.findByUserName(name);
//
//        user.subscribe(u->{
//            model.addAttribute("user", u);
//        });
//        currentUser.subscribe(u->{
//            model.addAttribute("currentUser", u);
//        });
//        return "userEdit";
//    }
//
//    @PostMapping("{name}/edit")
//    public String userEdit(@PathVariable String name,
//                           Model model,
//                           @RequestParam String username,
//                           @RequestParam String password,
//                           @RequestParam String birthdate,
//                           @RequestParam String email,
//                           @RequestParam("avatar") MultipartFile avatar,
//                           @AuthenticationPrincipal User current) throws IOException {
//        Mono<User> currentUser = userService.findById(current.getId());
//        Mono<User> user = userService.findByUserName(name);
//
//        user.subscribe(u->{
//            if (username != null && !username.isEmpty()) {
//                u.setUsername(username);
//                Set<UserMessage> messages = u.getMessages();
//                for (UserMessage message : messages) {
//                    message.setAuthorName(username);
//                    userService.saveMessage(message);
//                }
//            }
//
//            if (password != null && !password.isEmpty()) {
//                userService.setPassword(u, password);
//            }
//
//            try {
//                ControllerUtil.editAvatar(u, avatar, uploadPath);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//            if (email != null && !email.isEmpty()) {
//                u.setEmail(email);
//            }
//
//
//            userService.save(u);
//            currentUser.subscribe(c->{
//                model.addAttribute("currentUser", c);
//            });
//
//        });
//
//
//        return "redirect:/users/" + username;
//    }
//
//
//    @PostMapping("{username}/{action}")
//    public String addOrDelete(@PathVariable String username,
//                              @PathVariable String action,
//                              @AuthenticationPrincipal User current,
//                              Model model) {
//        Mono<User> currentUser = userService.findById(current.getId());
//        Mono<User> user = userService.findByUserName(username);
//        user.subscribe(u->{
//            currentUser.subscribe(c->{
//                if (action.equals("add")) {
//                    userService.addFriend(u, c);
//                } else if (action.equals("delete")) {
//                    userService.deleteFriend(u, c);
//                }
//                model.addAttribute("currentUser", c);
//            });
//        });
//
//
//
//        return "redirect:/users/" + username;
//    }
//
//    @GetMapping("{name}/chat")
//    public String chat(@PathVariable String name,
//                       @AuthenticationPrincipal User current,
//                       Model model) {
//        Mono<User> user = userService.findByUserName(name);
//        //Long id = user.getId();
//        Mono<User> currentUser = userService.findById(current.getId());
//
//        if (user.equals(currentUser)) {
//            return "redirect:/users/" + name;
//        }
//
//
//        currentUser.subscribe(c->{
//            user.subscribe(u->{
//                List<ChatMessage> messages = ControllerUtil.setToSortedList(c.getChatMessages(), u);
//
//                messages.addAll(ControllerUtil.setToSortedList(u.getChatMessages(), c));
//
//                messages.sort(Comparator.comparing(ChatMessage::getCreatedDate));
//
//                model.addAttribute("currentUser", c);
//                model.addAttribute("messages", messages);
//            });
//        });
//
//
//        return "chat";
//    }
//
//    @PostMapping("{name}/chat")
//    public String editChat(@PathVariable String name,
//                           @AuthenticationPrincipal User current,
//                           Model model,
//                           @RequestParam String text,
//                           @RequestParam MultipartFile file) throws IOException {
//
//        Mono<User> user = userService.findByUserName(name);
//        Mono<User> currentUser = userService.findById(current.getId());
//
//        currentUser.subscribe(c->{
//            user.subscribe(u->{
//                ChatMessage chatMessage = new ChatMessage();
//                chatMessage.setAuthorId(c.getId());
//                chatMessage.setAuthorName(c.getUsername());
//                LocalDateTime localDateTime = LocalDateTime.now();
//                chatMessage.setCreatedDate(Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant()));
//                try {
//                    ControllerUtil.setFile(chatMessage, file, uploadPath);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                chatMessage.setFriendId(u.getId());
//                chatMessage.setFriendName(u.getUsername());
//                chatMessage.setText(text);
//
//                userService.saveChatMessage(c, chatMessage);
//            });
//        });
//
//
//        return "redirect:/users/" + name + "/chat";
//
//    }


//}
