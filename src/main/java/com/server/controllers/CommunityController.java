package com.server.controllers;

import com.server.domain.CommMessage;
import com.server.domain.Community;
import com.server.domain.User;
import com.server.services.CommunityService;
import com.server.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import java.util.*;

/**
 * created by xev11
 */

@Controller
@RequestMapping("/communities")
public class CommunityController {

    @Autowired
    private UserService userService;

    @Autowired
    private CommunityService communityService;

    private Logger logger = LoggerFactory.getLogger(CommMessage.class);

    @Value("${upload.path}")
    private String uploadPath;

    @GetMapping("")
    public String communities(@AuthenticationPrincipal User current,
                              Model model) {
        User user = userService.findById(current.getId());
        Set<Community> communities = user.getCommunities();
        model.addAttribute("communities", communities);
        model.addAttribute("currentUser", user);
        return "communitylist";
    }


    @GetMapping("{name}")
    public String comm(@PathVariable String name,
                       Model umodel,
                       @AuthenticationPrincipal User current) {
        User user = userService.findById(current.getId());
        Community community = communityService.findByName(name);
        Set<CommMessage> m = community.getMessages();
        ArrayList<CommMessage> messages = new ArrayList<>(m);
        messages.sort((o1, o2) -> o2.getCreatedDate().compareTo(o1.getCreatedDate()));
        umodel.addAttribute("isSub", community.getCommunity_users().contains(user));
        umodel.addAttribute("subs", String.valueOf(community.getCommunity_users().size()));
        umodel.addAttribute("isCommAdmin", community.getAdmins().contains(user));
        umodel.addAttribute("comm", community);
        umodel.addAttribute("messages", messages);
        umodel.addAttribute("currentUser", user);
        return "community";
    }

    @PostMapping("{name}")
    public String addMessage(@PathVariable String name,
                             Model model,
                             @AuthenticationPrincipal User current,
                             @RequestParam String text,
                             @RequestParam MultipartFile file) throws IOException {

        User user = userService.findById(current.getId());
        Community community = communityService.findByName(name);
        CommMessage commMessage = new CommMessage();
        commMessage.setAuthorName(name);
        LocalDateTime localDateTime = LocalDateTime.now();
        commMessage.setCreatedDate(Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant()));
        saveFile(commMessage, file);
        commMessage.setText(text);
        communityService.addNewMessage(community, commMessage);

        return "redirect:/communities/" + name;
    }

    private void saveFile(CommMessage commMessage, @RequestParam MultipartFile file) throws IOException {
        if (file != null && !file.getOriginalFilename().isEmpty()) {
            File uploadDir = new File(uploadPath);

            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            String uuIDFile = UUID.randomUUID().toString();
            String resultFileName = uuIDFile + "." + file.getOriginalFilename();

            file.transferTo(new File(uploadPath + "/" + resultFileName));

            commMessage.setFilename(resultFileName);

        }
    }

    @PostMapping("{name}/{type}")
    public String action(@PathVariable String name,
                         @PathVariable String type,
                         @AuthenticationPrincipal User current,
                         Model model) {

        Community community = communityService.findByName(name);
        User currentUser = userService.findById(current.getId());

        if (type.equals("unsub")) {
            community.getCommunity_users().remove(currentUser);
            currentUser.getCommunities().remove(community);
            userService.save(currentUser);
            communityService.save(community);
        }

        if (type.equals("sub")) {
            community.getCommunity_users().add(currentUser);
            logger.info(currentUser.getCommunities().toString());
            currentUser.getCommunities().add(community);
            logger.info(currentUser.getCommunities().toString());
            userService.save(currentUser);
            communityService.save(community);
        }


        return "redirect:/communities/" + community.getName();
    }

    @GetMapping("{name}/edit")
    public String edit(@AuthenticationPrincipal User current,
                       @PathVariable String name,
                       Model model) {
        Community community = communityService.findByName(name);
        User currentUser = userService.findById(current.getId());
        if (!community.getAdmins().contains(currentUser)) {
            return "redirect:/communities/" + community.getName();
        }

        model.addAttribute("comm", community);
        model.addAttribute("currentUser", currentUser);
        return "communityEdit";
    }

    @PostMapping("{name}/edit")
    public String postEdit(@AuthenticationPrincipal User current,
                           @PathVariable String name,
                           Model model,
                           @RequestParam String commname,
                           @RequestParam MultipartFile avatar) throws IOException {
        Community community = communityService.findByName(name);
        User currentUser = userService.findById(current.getId());

        if (commname != null && !commname.isEmpty()) {
            community.setName(commname);
            Set<CommMessage> messages = community.getMessages();
            for (CommMessage message : messages) {
                message.setAuthorName(commname);
            }

        }

        ControllerUtil.editAvatar(community, avatar, uploadPath);

        model.addAttribute("currentUser", currentUser);
        return "redirect:/communities/" + community.getName();
    }
}
