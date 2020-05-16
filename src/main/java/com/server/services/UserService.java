package com.server.services;

import com.server.domain.*;
import com.server.repos.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

/**
 * created by xev11
 */

@Service("userService")
public class UserService extends MapReactiveUserDetailsService{

    private UserRepo userRepo;


    public UserService(UserRepo userRepo, PasswordEncoder passwordEncoder) {
        super(new UserDetails() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return Collections.singleton(Role.ADMIN);
            }

            @Override
            public String getPassword() {
                return passwordEncoder.encode("password");
            }

            @Override
            public String getUsername() {
                return "user";
            }

            @Override
            public boolean isAccountNonExpired() {
                return true;
            }

            @Override
            public boolean isAccountNonLocked() {
                return true;
            }

            @Override
            public boolean isCredentialsNonExpired() {
                return true;
            }

            @Override
            public boolean isEnabled() {
                return true;
            }
        });
        this.userRepo = userRepo;
    }

    @Autowired
    private UserMessageRepo userMessageRepo;

    @Autowired
    private ChatMessageRepo chatMessageRepo;


    @Autowired
    private CommRepo commRepo;

    @Autowired
    private AlbumRepo albumRepo;

    @Autowired
    private PhotoRepo photoRepo;

    @Autowired
    private CommentRepo commentRepo;

    @Autowired
    private CommMessageRepo commMessageRepo;

    @Value("${upload.path}")
    private String uploadPath;


    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return userRepo.findByUsername(username).map(user->user);
    }

    public Mono<User> findByUserName(String username) {
        return userRepo.findByUsername(username);
    }

    public Mono<User> findById(String id) {
        return userRepo.findById(id);
    }

    public Mono<User> addNewMessage(User user, UserMessage userMessage) {
        Mono<UserMessage> save = userMessageRepo.save(userMessage);
        return save.flatMap(m->{
            user.getMessages().add(m);
            return save(user);
        });
    }

    public void setPassword(User user, String password) {
        user.setPassword(password);
    }

    public Mono<User> save(User user) {
        return userRepo.save(user);
    }

    public Flux<User> save(Mono<User> user){
        return userRepo.saveAll(user);
    }

    private Logger logger = LoggerFactory.getLogger(UserService.class);

    public void addFriend(User user, User currentUser) {
        user.getFriends().add(currentUser.getUsername());
        currentUser.getFriends().add(user.getUsername());
        userRepo.save(currentUser).hasElement().subscribe(f->logger.info(f?"1 saved":"1 not saved"));
        userRepo.save(user).hasElement().subscribe(f->logger.info(f?"2 saved":"2 not saved"));
    }

    public void deleteFriend(User user, User currentUser) {
        user.getFriends().remove(currentUser.getUsername());
        currentUser.getFriends().remove(user.getUsername());
        userRepo.save(user).hasElement().subscribe(f->logger.info(f?"1 saved":"1 not saved"));;
        userRepo.save(currentUser).hasElement().subscribe(f->logger.info(f?"2 saved":"2 not saved"));;
    }


    public void saveMessage(UserMessage userMessage) {
        userMessageRepo.save(userMessage);
    }

    public Mono<User> saveChatMessage(User currentUser, ChatMessage chatMessage) {
        return chatMessageRepo.save(chatMessage).flatMap(message->{
            currentUser.getChatMessages().add(message);
            return userRepo.save(currentUser);
        });

    }

    public void addNewCommunity(Community community, User user) {
        if (community.getAvatar() == null) {
            community.setAvatar("default.jpg");
        }
        Mono<Community> save = commRepo.save(community);
        save.subscribe(c->{
            user.getCommunities().add(c.getName());
            userRepo.save(user);
        });

    }

    public Flux<User> findAll() {
        return userRepo.findAll();
    }


    public void addNewAlbum(User currentUser, String albumname) {
        Album album = new Album();
        album.setName(albumname);
        album.setUserid(currentUser.getId());
        Mono<Album> save = albumRepo.save(album);
        save.subscribe(a->{
            currentUser.getAlbums().add(a);
            userRepo.save(currentUser);
        });
    }

    public void addNewPhoto(Photo photo, Album album, User currentUser) {
        Mono<Photo> save = photoRepo.save(photo);
        save.subscribe(p->{
            album.getPhotos().add(p);
            Mono<Album> save1 = albumRepo.save(album);
            save1.subscribe(a->{
                currentUser.getAlbums().add(a);
                userRepo.save(currentUser);
            });
        });
    }

    public void saveAlbum(Album album, User currentUser) {
        Mono<Album> save = albumRepo.save(album);
        save.subscribe(a->{
            currentUser.getAlbums().add(a);
            userRepo.save(currentUser);
        });
    }

    public Mono<Album> findAlbum(String albumname) {
        return albumRepo.findByName(albumname);
    }

    public void addNewComment(String uni, String text, MultipartFile photo, User currentUser){
        LocalDateTime localDateTime = LocalDateTime.now();
        Comment comment = new Comment(Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant()),
                currentUser.getUsername(), currentUser.getId(), text);

        comment.setId(UUID.randomUUID().toString());

        //ControllerUtil.commentPhoto(comment, photo, uploadPath);
         commentRepo.save(comment).flatMap(c->{
             if (uni.endsWith("community")) {
                 return commMessageRepo.findByUni(uni).flatMap(m->{
                     m.getComments().add(c);
                     return commMessageRepo.save(m);
                 });
             } else if(uni.endsWith("user")){
                 return userMessageRepo.findByUni(uni).flatMap(m->{
                     m.getComments().add(c);
                     return userMessageRepo.save(m);
                 });
             } else if(uni.startsWith("photo")){
                 return photoRepo.findByUni(uni).flatMap(p->{
                     p.getComments().add(c);
                     return photoRepo.save(p);
                 });
             }
             return Mono.empty();
        }).subscribe(o -> logger.info(o==null?"no saved":"saved"));
    }

    public Mono<List<Message>> getMessagesForNews(User currentUser){
        Flux<Message> commMessages = commMessageRepo.findAll().filter(commMessage -> currentUser.getCommunities().contains(commMessage.getAuthorName())).map(mes->mes);
        Flux<Message> friendsMessages = userMessageRepo.findAll().filter(userMessage -> currentUser.getFriends().contains(userMessage.getAuthorName())).map(mes->mes);
        Flux<Message> allMessages = commMessages.concatWith(friendsMessages).sort((o1, o2) -> o2.getCreatedDate().compareTo(o1.getCreatedDate()));
        return allMessages.collectList();
    }

    public Mono<List<UserMessage>> getUserMessages(User user){
        return userMessageRepo.findAll().filter(userMessage -> user.getUsername().equals(userMessage.getAuthorName())).collectList();
    }

    /*public Flux<Message> findMessages(User currentUser) {
        List<Message> messages = new ArrayList<>();
        Set<Community> communities = currentUser.getCommunities();
        for (Community community : communities) {
            if (community.getCommunity_users().contains(currentUser)) {
                messages.addAll(community.getMessages());
            }
        }
        Set<User> friends = currentUser.getFriends();
        for (User friend : friends) {
            messages.addAll(friend.getMessages());
        }

        messages.sort((o1, o2) -> o2.getCreatedDate().compareTo(o1.getCreatedDate()));


        for (Message message : messages) {
            Set<Comment> comments = message.getComments().stream().sorted(Comparator.comparing(Comment::getCreatedDate)).collect(Collectors.toCollection(LinkedHashSet::new));
            message.setComments(comments);
        }

        return Flux.fromIterable(messages);

    } */
    public Mono<Message> likeMessage(String uni, User currentUser, String where) {
        if (where.equals("touser")) {
            return userMessageRepo.findByUni(uni).flatMap(m->{
                m.getLikes().add(currentUser);
                return userMessageRepo.save(m).map(mes->mes);
            });
        } else if (where.equals("tomessage")) {
            return commMessageRepo.findByUni(uni).flatMap(m->{
                m.getLikes().add(currentUser);
                return commMessageRepo.save(m).map(mes->mes);
            });
        } else return Mono.empty();
    }

    public Mono<Message> unLikeMessage(String uni, User currentUser, String where) {
        if (where.equals("touser")) {
            return userMessageRepo.findByUni(uni).flatMap(m->{
                m.getLikes().remove(currentUser);
                return userMessageRepo.save(m).map(mes->mes);
            });
        } else if (where.equals("tomessage")) {
            return commMessageRepo.findByUni(uni).flatMap(m->{
                m.getLikes().remove(currentUser);
                return commMessageRepo.save(m).map(mes->mes);
            });
        } else return Mono.empty();
    }

    public void addLikeToComment(String id, User currentUser) {
        Mono<Comment> comment = commentRepo.findById(id);
        comment.subscribe(c->{
            c.getLikes().add(currentUser.getUsername());
            commentRepo.save(c);
        });

    }

    public void unLikeComment(String id, User currentUser) {
        Mono<Comment> comment = commentRepo.findById(id);
        comment.subscribe(m->{
            m.getLikes().remove(currentUser.getUsername());
            commentRepo.save(m);
        });
    }

    public void addLikeToPhoto(String name, User currentUser) {
        Mono<Photo> photo = photoRepo.findByUni(name);
        photo.subscribe(p->{
            p.getLikes().add(currentUser);
            photoRepo.save(p);
        });
    }

    public void unLikePhoto(String name, User currentUser) {
        Mono<Photo> photo = photoRepo.findByUni(name);
        photo.subscribe(p->{
            p.getLikes().remove(currentUser);
            photoRepo.save(p);
        });
    }

    public Flux<Message> findUserMessagesById(String id) {
        return userMessageRepo.findByAuthorId(id).map(userMessage -> userMessage);
    }

}
