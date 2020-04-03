package com.server.controllers;

import com.server.domain.*;
import com.server.services.CommunityService;
import com.server.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.*;

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
        User currentUser = userService.findById(current.getId());
        List<Message> messages = userService.findMessages(currentUser);
        model.addAttribute("messages", messages);
        model.addAttribute("currentUser", currentUser);

        return "news";

    }

    @GetMapping("/test")
    @ResponseBody
    public Collection<User> test() {
        return userService.findAll();
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
        user.setBirthdate(new Date(new GregorianCalendar(Integer.parseInt(strings[0]), Integer.parseInt(strings[1]), Integer.parseInt(strings[2])).getTime().getTime()));
        userService.save(user);
        return "redirect:/login";
    }

    @GetMapping("/{name}/albums")
    public String albums(@PathVariable String name,
                         @AuthenticationPrincipal User current,
                         Model model) {
        User user = userService.findByUserName(name);
        User currentUser = userService.findById(current.getId());
        model.addAttribute("isCurrentUser", user.equals(currentUser));
        model.addAttribute("currentUser", currentUser);
        Set<Album> albums = user.getAlbums();
        model.addAttribute("albums", albums);
        return "albums";
    }

    @GetMapping("/{name}/albums/createNew")
    public String createNew(@PathVariable String name,
                            @AuthenticationPrincipal User current,
                            Model model) {
        User user = userService.findByUserName(name);
        User currentUser = userService.findById(current.getId());
        if (user.equals(currentUser)) {
            model.addAttribute("currentUser", currentUser);
            return "newAlbum";
        }

        return "redirect:/" + currentUser.getUsername() + "/albums";
    }

    @PostMapping("/{name}/albums/createNew")
    public String addNewAlbum(@PathVariable String name,
                              @AuthenticationPrincipal User current,
                              Model model,
                              @RequestParam String albumname) {
        User user = userService.findByUserName(name);
        User currentUser = userService.findById(current.getId());
        if (user.equals(currentUser)) {
            userService.addNewAlbum(currentUser, albumname);
        }

        return "redirect:/" + currentUser.getUsername() + "/albums";
    }

    @GetMapping("/{name}/albums/{albumname}")
    public String album(@PathVariable String name,
                        @PathVariable String albumname,
                        @AuthenticationPrincipal User current,
                        Model model) {
        User user = userService.findByUserName(name);
        User currentUser = userService.findById(current.getId());
        model.addAttribute("isCurrentUser", user.equals(currentUser));
        Album album = userService.findAlbum(albumname);
        if (album == null || !user.getAlbums().contains(album)) {
            return "redirect:/" + name + "/albums";
        }
        ArrayList<Photo> p = new ArrayList<>(album.getPhotos());
        ArrayList<String> photos = new ArrayList<>();

        for (Photo photo : p) {
            photos.add(photo.getName());
        }
        model.addAttribute("albumname", albumname);
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("photos", photos);
        return "photos";

    }

    @PostMapping("/{name}/albums/{albumname}/addPhoto")
    public String addPhoto(@PathVariable String name,
                           @PathVariable String albumname,
                           @AuthenticationPrincipal User current,
                           @RequestParam MultipartFile photo,
                           Model model) throws IOException {
        User user = userService.findByUserName(name);
        User currentUser = userService.findById(current.getId());
        Album album = userService.findAlbum(albumname);
        if (album == null || !user.getAlbums().contains(album)) {
            return "redirect:/" + name + "/albums";
        }
        ControllerUtil.addPhoto(album, photo, uploadPath, userService, currentUser);
        return "redirect:/" + name + "/albums/" + album.getName();
    }


    @PostMapping("/{uni}/addComment")
    public String addComment(@PathVariable String uni,
                             @AuthenticationPrincipal User current,
                             @RequestParam String text,
                             @RequestParam MultipartFile photo,
                             RedirectAttributes redirectAttributes,
                             @RequestHeader(required = false) String referer) throws IOException {
        User currentUser = userService.findById(current.getId());
        userService.addNewComment(uni, text, photo, currentUser);
        UriComponents components = UriComponentsBuilder.fromHttpUrl(referer).build();

        components.getQueryParams()
                .forEach(redirectAttributes::addAttribute);
        return "redirect:" + components.getPath();
    }

    @GetMapping("/{where}/{uni}/{action}")
    public String doAction(@PathVariable String uni,
                           @PathVariable String where,
                           @PathVariable String action,
                           RedirectAttributes redirectAttributes,
                           @RequestHeader(required = false) String referer,
                           @AuthenticationPrincipal User current) {

        User currentUser = userService.findById(current.getId());
        if (action.equals("like")) {
            userService.likeMessage(uni, currentUser, where);
        } else if (action.equals("unLike")) {
            userService.unLikeMessage(uni, currentUser, where);
        }
        UriComponents components = UriComponentsBuilder.fromHttpUrl(referer).build();

        components.getQueryParams()
                .forEach(redirectAttributes::addAttribute);

        return "redirect:" + components.getPath();
    }

    @GetMapping("/{action}/{id}/toComment")
    public String doAcToCom(@PathVariable String action,
                            @PathVariable Long id,
                            @AuthenticationPrincipal User current,
                            RedirectAttributes redirectAttributes,
                            @RequestHeader(required = false) String referer) {
        User currentUser = userService.findById(current.getId());
        if (action.equals("like")) {
            userService.addLikeToComment(id, currentUser);
        } else if (action.equals("unLike")) {
            userService.unLikeComment(id, currentUser);
        }
        UriComponents components = UriComponentsBuilder.fromHttpUrl(referer).build();

        components.getQueryParams()
                .forEach(redirectAttributes::addAttribute);

        return "redirect:" + components.getPath();
    }


}
