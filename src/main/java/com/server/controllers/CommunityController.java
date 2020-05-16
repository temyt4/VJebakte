package com.server.controllers;

import com.server.domain.CommMessage;
import com.server.domain.Comment;
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
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

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
}

//    @GetMapping("")
//    public String communities(@AuthenticationPrincipal User current,
//                              Model model) {
//        Mono<User> user = userService.findById(current.getId());
//        user.subscribe(u->{
//            model.addAttribute("communities", u.getCommunities());
//            model.addAttribute("currentUser", u);
//        });
//
//
//        return "communitylist";
//    }
//
//
//    @GetMapping("{name}")
//    public String comm(@PathVariable String name,
//                       Model umodel,
//                       @AuthenticationPrincipal User current) {
//        Mono<User> user = userService.findById(current.getId());
//        Mono<Community> community = communityService.findByName(name);
//
//        community.subscribe(com->{
//            user.subscribe(u->{
//                Set<CommMessage> m = com.getMessages();
//
//                ArrayList<CommMessage> messages = new ArrayList<>(m);
//                for (CommMessage message : messages) {
//                    Set<Comment> comments = message.getComments().stream().sorted(Comparator.comparing(Comment::getCreatedDate)).collect(Collectors.toCollection(LinkedHashSet::new));
//                    message.setComments(comments);
//                }
//
//                messages.sort((o1, o2) -> o2.getCreatedDate().compareTo(o1.getCreatedDate()));
//                umodel.addAttribute("isSub", com.getCommunity_users().contains(u));
//                umodel.addAttribute("subs", String.valueOf(com.getCommunity_users().size()));
//                umodel.addAttribute("isCommAdmin", com.getAdmins().contains(u));
//                umodel.addAttribute("comm", com);
//                umodel.addAttribute("messages", messages);
//                umodel.addAttribute("currentUser", u);
//            });
//        });
//
//
//        return "community";
//    }
//
//    @PostMapping("{name}")
//    public String addMessage(@PathVariable String name,
//                             Model model,
//                             @AuthenticationPrincipal User current,
//                             @RequestParam String text,
//                             @RequestParam MultipartFile file) throws IOException {
//
//        Mono<Community> community = communityService.findByName(name);
//        community.subscribe(com->{
//            CommMessage commMessage = new CommMessage();
//            commMessage.setAuthorName(name);
//            LocalDateTime localDateTime = LocalDateTime.now();
//            commMessage.setCreatedDate(Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant()));
//            try {
//                saveFile(commMessage, file);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            commMessage.setText(text);
//            communityService.addNewMessage(com, commMessage);
//        });
//
//
//        return "redirect:/communities/" + name;
//    }
//
//    private void saveFile(CommMessage commMessage, @RequestParam MultipartFile file) throws IOException {
//        if (file != null && !file.getOriginalFilename().isEmpty()) {
//            File uploadDir = new File(uploadPath);
//
//            if (!uploadDir.exists()) {
//                uploadDir.mkdir();
//            }
//
//            String uuIDFile = UUID.randomUUID().toString();
//            String resultFileName = uuIDFile + "." + file.getOriginalFilename();
//
//            file.transferTo(new File(uploadPath + "/" + resultFileName));
//
//            commMessage.setFilename(resultFileName);
//
//        }
//    }
//
//    @PostMapping("{name}/{type}")
//    public String action(@PathVariable String name,
//                         @PathVariable String type,
//                         @AuthenticationPrincipal User current,
//                         Model model) {
//
//        Mono<Community> community = communityService.findByName(name);
//        Mono<User> currentUser = userService.findById(current.getId());
//
//        community.subscribe(com->{
//            currentUser.subscribe(c->{
//                if (type.equals("unsub")) {
//                    com.getCommunity_users().remove(c);
//                    c.getCommunities().remove(com);
//                    userService.save(c);
//                    communityService.save(com);
//                }
//
//                if (type.equals("sub")) {
//                    com.getCommunity_users().add(c);
//                    logger.info(c.getCommunities().toString());
//                    c.getCommunities().add(com.getName());
//                    logger.info(c.getCommunities().toString());
//                    userService.save(c);
//                    communityService.save(com);
//                }
//            });
//        });
//
//
//
//
//        return "redirect:/communities/" + name;
//    }
//
//    @GetMapping("{name}/edit")
//    public ModelAndViewContainer edit(@AuthenticationPrincipal User current,
//                                      @PathVariable String name,
//                                      Model model) {
//        Mono<Community> community = communityService.findByName(name);
//        Mono<User> currentUser = userService.findById(current.getId());
//
//        ModelAndViewContainer modelAndViewContainer = new ModelAndViewContainer();
//
//        community.subscribe(com->{
//            currentUser.subscribe(u->{
//                if (!com.getAdmins().contains(u)) {
//                    modelAndViewContainer.setRedirectModelScenario(true);
//                    modelAndViewContainer.setViewName("redirect:/communities/" + name);
//                } else {
//
//                    modelAndViewContainer.addAttribute("comm", community);
//                    modelAndViewContainer.addAttribute("currentUser", currentUser);
//                    modelAndViewContainer.setViewName("communityEdit");
//                }
//            });
//        });
//
//        return modelAndViewContainer;
//    }
//
//    @PostMapping("{name}/edit")
//    public String postEdit(@AuthenticationPrincipal User current,
//                           @PathVariable String name,
//                           Model model,
//                           @RequestParam String commname,
//                           @RequestParam MultipartFile avatar) throws IOException {
//        Mono<Community> community = communityService.findByName(name);
//        Mono<User> currentUser = userService.findById(current.getId());
//
//        community.subscribe(com->{
//            if (commname != null && !commname.isEmpty()) {
//                com.setName(commname);
//                Set<CommMessage> messages = com.getMessages();
//                for (CommMessage message : messages) {
//                    message.setAuthorName(commname);
//                }
//
//            }
//
//            try {
//                ControllerUtil.editAvatar(com, avatar, uploadPath);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//            model.addAttribute("currentUser", currentUser);
//        });
//
//        return "redirect:/communities/" + commname;
//    }
//
//    @GetMapping("addNew")
//    public String addNew(@AuthenticationPrincipal User current,
//                         Model model) {
//
//        Mono<User> currentUser = userService.findById(current.getId());
//
//        currentUser.subscribe(c->model.addAttribute("currentUser", c));
//
//
//        return "newCommunity";
//    }
//
//    @PostMapping("addNew")
//    public String newCom(@AuthenticationPrincipal User current,
//                         Model model,
//                         @RequestParam String name,
//                         @RequestParam MultipartFile avatar) throws IOException {
//
//        Mono<User> currentUser = userService.findById(current.getId());
//        currentUser.subscribe(c->{
//            Community community = new Community();
//            try {
//                ControllerUtil.editAvatar(community, avatar, uploadPath);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            community.setName(name);
//            community.setAdmins(Collections.singleton(c));
//
//            userService.addNewCommunity(community, c);
//        });
//
//
//        return "redirect:/communities/" + name;
//
//    }
//}
