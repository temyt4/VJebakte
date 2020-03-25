package com.server.controllers;

import com.server.domain.User;
import com.server.domain.dto.MessageDto;
import com.server.services.CommunityService;
import com.server.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.GregorianCalendar;
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

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${upload.path}")
    private String uploadPath;

    @GetMapping("")
    public String re() {
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

    @GetMapping("/registration")
    public String reg() {
        return "registration";
    }

    @PostMapping("/registration")
    public String registration(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String birthdate,
            @RequestParam MultipartFile avatar,
            @RequestParam String email,
            Model model) throws IOException {

        User u = userService.findByUserName(username);
        if (u != null) {
            return "redirect:/registration";
        }
        User user = new User();
        ControllerUtil.editAvatar(user, avatar, uploadPath);
        if (StringUtils.isEmpty(user.getUser_avatar())) {
            user.setUser_avatar("default.jpg");
        }
        user.setEmail(email);
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        String[] strings = birthdate.split("-");
        user.setBirthdate(new Date(new GregorianCalendar(Integer.valueOf(strings[0]), Integer.valueOf(strings[1]), Integer.valueOf(strings[2])).getTime().getTime()));
        userService.save(user);
        return "redirect:/login";
    }


}
