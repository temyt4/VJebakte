package com.server.controllers;

import com.server.domain.*;
import com.server.services.CommunityService;
import com.server.services.UserService;
import freemarker.cache.TemplateNameFormat;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.freemarker.FreeMarkerAutoConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticatedPrincipal;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.server.csrf.DefaultCsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.result.view.View;
import org.springframework.web.reactive.result.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

import static com.server.handlers.MainHandler.DEFAULT_TOKEN;

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

//    @GetMapping("/news")
//    public Mono<String> main(@AuthenticationPrincipal UserDetails current,
//                       Model model) {
//
//            Mono<User> currentUser = userService.findByUserName(current.getUsername());
//            currentUser.subscribe(u-> {
//                model.addAttribute("currentUser", u);
//                model.addAttribute("messages", userService.findMessages(u).toIterable());
//            });
//
//        return Mono.just("news");
//
//    }


//    @GetMapping("/friends")
//    public ModelAndViewContainer friends(@AuthenticationPrincipal UserDetails current,
//                          Model model) {
//        Mono<User> user = userService.findByUserName(current.getUsername());
//        ModelAndViewContainer modelAndViewContainer = new ModelAndViewContainer();
//        user.subscribe(u->{
//            modelAndViewContainer.addAttribute("friends", u.getFriends());
//            modelAndViewContainer.addAttribute("currentUser", u);
//        });
//        modelAndViewContainer.setViewName("friends");
//
//        return modelAndViewContainer;
//    }
//
//    @GetMapping("/registration")
//    public String reg(ServerWebExchange serverWebExchange, Model model) {
//        Mono<DefaultCsrfToken> csrfToken = serverWebExchange.getAttribute(DEFAULT_TOKEN);
//        csrfToken.subscribe(t->model.addAttribute("_csrf", t));
//        return "registration";
//    }
//
//    @PostMapping("/registration")
//    public String registration(
//            @RequestParam String username,
//            @RequestParam String password,
//            @RequestParam String birthdate,
//            @RequestParam MultipartFile avatar,
//            @RequestParam String email,
//            ServerWebExchange serverWebExchange) throws IOException {
//
//        Mono<User> register = userService.findByUserName(username);
//
//        AtomicBoolean f = new AtomicBoolean(false);
//        register.subscribe(u->{
//            if (u != null) {
//                //return "redirect:/registration";
//                f.set(false);
//                return;
//            }
//            User user = new User();
//            try {
//                ControllerUtil.editAvatar(user, avatar, uploadPath);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            if (StringUtils.isEmpty(user.getUser_avatar())) {
//                user.setUser_avatar("default.jpg");
//            }
//            user.setEmail(email);
//            user.setUsername(username);
//            user.setPassword(password);
//            String[] strings = birthdate.split("-");
//            user.setBirthdate(new Date(new GregorianCalendar(Integer.parseInt(strings[0]), Integer.parseInt(strings[1]), Integer.parseInt(strings[2])).getTime().getTime()));
//            userService.save(user);
//            f.set(true);
//        });
//
//        String s = f.get()?"login":"registration";
//
//        return "redirect:/"+ s;
//    }
//
//    @GetMapping("/{name}/albums")
//    public String albums(@PathVariable String name,
//                         @AuthenticationPrincipal User current,
//                         Model model) {
//        Mono<User> user = userService.findByUserName(name);
//        Mono<User> currentUser = userService.findById(current.getId());
//        user.subscribe(u->{
//            model.addAttribute("albums", u.getAlbums());
//            currentUser.subscribe(u1->{
//                model.addAttribute("currentUser", u1);
//                model.addAttribute("isCurrentUser", u.equals(u1));
//            });
//
//        });
//
//        return "albums";
//    }
//
//    @GetMapping("/{name}/albums/createNew")
//    public String createNew(@PathVariable String name,
//                            @AuthenticationPrincipal User current,
//                            Model model) {
//        Mono<User> user = userService.findByUserName(name);
//        Mono<User> currentUser = userService.findById(current.getId());
//        if (user.equals(currentUser)) {
//            currentUser.subscribe(u->{
//                model.addAttribute("currentUser", u);
//            });
//            return "newAlbum";
//        }
//
//        return "redirect:/" + name + "/albums";
//    }
//
//    @PostMapping("/{name}/albums/createNew")
//    public String addNewAlbum(@PathVariable String name,
//                              @AuthenticationPrincipal User current,
//                              Model model,
//                              @RequestParam String albumname) {
//        Mono<User> user = userService.findByUserName(name);
//        Mono<User> currentUser = userService.findById(current.getId());
//        if (user.equals(currentUser)) {
//            currentUser.subscribe(u->{
//                userService.addNewAlbum(u, albumname);
//            });
//        }
//
//        return "redirect:/" + name + "/albums";
//    }
//
//    @GetMapping("/{name}/albums/{albumname}")
//    public ModelAndViewContainer album(@PathVariable String name,
//                                       @PathVariable String albumname,
//                                       @AuthenticationPrincipal User current) {
//
//        ModelAndViewContainer modelAndViewContainer = new ModelAndViewContainer();
//        Mono<User> user = userService.findByUserName(name);
//        Mono<User> currentUser = userService.findById(current.getId());
//        modelAndViewContainer.addAttribute("isCurrentUser", user.equals(currentUser));
//        Mono<Album> album = userService.findAlbum(albumname);
//
//        album.subscribe(a->{
//
//            user.subscribe(u-> {
//                if (a == null || !u.getAlbums().contains(a)) {
//                    modelAndViewContainer.setRedirectModelScenario(true);
//                    modelAndViewContainer.setViewName("redirect:/" + name + "/albums");
//                } else {
//                    ArrayList<Photo> photos = new ArrayList<>(a.getPhotos());
//                    modelAndViewContainer.addAttribute("albumname", albumname);
//                    modelAndViewContainer.addAttribute("currentUser", u);
//                    modelAndViewContainer.addAttribute("photos", photos);
//                    modelAndViewContainer.setViewName("photos");
//                }
//            });
//        });
//        return modelAndViewContainer;
//
//    }
//
//    @PostMapping("/{name}/albums/{albumname}/addPhoto")
//    public ModelAndViewContainer addPhoto(@PathVariable String name,
//                         @PathVariable String albumname,
//                         @AuthenticationPrincipal User current,
//                         @RequestParam MultipartFile photo) throws IOException {
//        Mono<User> user = userService.findByUserName(name);
//        Mono<User> currentUser = userService.findById(current.getId());
//        Mono<Album> album = userService.findAlbum(albumname);
//        ModelAndViewContainer modelAndViewContainer = new ModelAndViewContainer();
//
//        album.subscribe(a->{
//            user.subscribe(u->{
//                if (a == null || !u.getAlbums().contains(a)) {
//                    modelAndViewContainer.setRedirectModelScenario(true);
//                     modelAndViewContainer.setViewName("redirect:/" + name + "/albums");
//                } else {
//                    try {
//                        ControllerUtil.addPhoto(a, photo, uploadPath, userService, u);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    modelAndViewContainer.setRedirectModelScenario(true);
//                    modelAndViewContainer.setViewName("redirect:/" + name + "/albums/" + a.getName());
//                }
//            });
//        });
//        return modelAndViewContainer;
//    }
//

//    @PostMapping("/{uni}/addComment")
//    public String addComment(@PathVariable String uni,
//                             @AuthenticationPrincipal User current,
//                             @RequestParam String text,
//                             @RequestParam MultipartFile photo,
//                             @RequestHeader(required = false) String referer) throws IOException {
//        Mono<User> currentUser = userService.findById(current.getId());
//        currentUser.subscribe(u->{
//            try {
//                userService.addNewComment(uni, text, photo, u);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        });
//
//
//        return "redirect:/news";
//    }
//
//    @GetMapping("/{where}/{uni}/{action}")
//    public String doAction(@PathVariable String uni,
//                           @PathVariable String where,
//                           @PathVariable String action,
//                           @RequestHeader(required = false) String referer,
//                           @AuthenticationPrincipal User current) {
//
//        Mono<User> currentUser = userService.findById(current.getId());
//
//        currentUser.subscribe(u->{
//            if (action.equals("like")) {
//                userService.likeMessage(uni, u, where);
//            } else if (action.equals("unLike")) {
//                userService.unLikeMessage(uni, u, where);
//            }
//        });
//
//        return "redirect:/news";
//    }
//
//    @GetMapping("/{action}/{id}/toComment")
//    public String doAcToCom(@PathVariable String action,
//                            @PathVariable Long id,
//                            @AuthenticationPrincipal User current,
//                            @RequestHeader(required = false) String referer) {
//        Mono<User> currentUser = userService.findById(current.getId());
//
//        currentUser.subscribe(u->{
//            if (action.equals("like")) {
//                userService.addLikeToComment(id, u);
//            } else if (action.equals("unLike")) {
//                userService.unLikeComment(id, u);
//            }
//        });
//
//
//        return "redirect:/news";
//    }
//
//    @GetMapping("/photo/{name}/{action}")
//    public String photoCom(@PathVariable String name,
//                           @PathVariable String action,
//                           @AuthenticationPrincipal User current,
//                           @RequestHeader(required = false) String referer){
//
//        Mono<User> currentUser = userService.findById(current.getId());
//
//        currentUser.subscribe(u->{
//            if (action.equals("like")) {
//                userService.addLikeToPhoto(name, u);
//            } else if (action.equals("unLike")) {
//                userService.unLikePhoto(name, u);
//            }
//        });
//
//
//        return "redirect:/news";
//    }
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    @GetMapping("/u")
//    @ResponseBody
//    public Flux<User> getUsers(){
//        //User user = new com.server.domain.User(1L, "user", passwordEncoder.encode("password"), Collections.singleton(Role.ADMIN));
//        //userService.save(user);
//
//        return userService.findAll();
//    }
//
//    @Autowired
//    private FreeMarkerConfigurer configurer;
//
//    @GetMapping("/conf")
//    @ResponseBody
//    public List<String> huy() throws IOException {
//        List<String> list = new ArrayList();
//        //list.add(configurer.getConfiguration().getTemplateLoader().toString());
//        list.add(configurer.getConfiguration().getTemplateNameFormat().toString());
//
//        return list;
//    }
//
//    @GetMapping("/c")
//    @ResponseBody
//    public String c(@AuthenticationPrincipal UserDetails user){
//        return user.getUsername()+"pizda";
//    }
//
//    @GetMapping("/co")
//    @ResponseBody
//    public String co(@AuthenticationPrincipal User user){
//        return user.getUsername()+"pizda";
//    }
//

}
