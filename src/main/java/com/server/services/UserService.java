package com.server.services;

import com.server.controllers.ControllerUtil;
import com.server.domain.*;
import com.server.domain.dto.MessageDto;
import com.server.domain.dto.UserDto;
import com.server.repos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

/**
 * created by xev11
 */

@Service("userService")
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private UserMessageRepo userMessageRepo;

    @Autowired
    private ChatMessageRepo chatMessageRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

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
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(s);
        if (user == null) {
            throw new UsernameNotFoundException("not found user");
        }
        return user;
    }

    public User findByUserName(String username) {
        return userRepo.findByUsername(username);
    }

    public User findById(Long id) {
        return userRepo.findById(id).get();
    }

    public void addNewMessage(User user, UserMessage userMessage) {
        UserMessage save = userMessageRepo.save(userMessage);
        user.getMessages().add(save);
        userRepo.save(user);
    }

    public void setPassword(User user, String password) {
        user.setPassword(passwordEncoder.encode(password));
    }

    public void save(@NotNull User user) {
        userRepo.save(user);
    }

    public void addFriend(User user, User currentUser) {
        user.getFriends().add(currentUser);
        currentUser.getFriends().add(user);
        userRepo.save(currentUser);
        userRepo.save(user);
        userRepo.flush();
    }

    public void deleteFriend(User user, User currentUser) {
        user.getFriends().remove(currentUser);
        currentUser.getFriends().remove(user);
        userRepo.saveAndFlush(user);
        userRepo.saveAndFlush(currentUser);
        userRepo.flush();
    }

    public void flush() {
        userRepo.flush();
    }

    public UserDto findUserDtoByUsername(String username) {
        return userRepo.findDtoByUsername(username);
    }

    public UserDto findUserDtoById(Long id) {
        return userRepo.findDtoById(id);
    }

    public void saveMessage(UserMessage userMessage) {
        userMessageRepo.save(userMessage);
    }

    public void saveChatMessage(User currentUser, ChatMessage chatMessage) {
        ChatMessage save = chatMessageRepo.save(chatMessage);
        currentUser.getChatMessages().add(save);
        userRepo.save(currentUser);
    }

    public Set<MessageDto> findUserMessageDtoById(Long id) {
        return userMessageRepo.findDtoByAuthorId(id);
    }

    public void addNewCommunity(Community community, User user) {
        if (community.getAvatar() == null) {
            community.setAvatar("default.jpg");
        }
        Community save = commRepo.save(community);
        user.getCommunities().add(save);
        userRepo.save(user);
    }

    public List<User> findAll() {
        return userRepo.findAll();
    }


    public void addNewAlbum(User currentUser, String albumname) {
        Album album = new Album();
        album.setName(albumname);
        album.setUserid(currentUser.getId());
        Album save = albumRepo.save(album);
        currentUser.getAlbums().add(save);
        userRepo.save(currentUser);
    }

    public void addNewPhoto(Photo photo, Album album, User currentUser) {
        Photo save = photoRepo.save(photo);
        album.getPhotos().add(save);
        Album save1 = albumRepo.save(album);
        currentUser.getAlbums().add(save1);
        userRepo.save(currentUser);
    }

    public void saveAlbum(Album album, User currentUser) {
        Album save = albumRepo.save(album);
        currentUser.getAlbums().add(save);
        userRepo.save(currentUser);
    }

    public Album findAlbum(String albumname) {
        return albumRepo.findByName(albumname);
    }

    public void addNewComment(String uni, String text, MultipartFile photo, User currentUser) throws IOException {
        LocalDateTime localDateTime = LocalDateTime.now();
        Comment comment = new Comment(Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant()),
                currentUser.getUsername(), currentUser.getId(), text);

        ControllerUtil.commentPhoto(comment, photo, uploadPath);
        Comment saveComment = commentRepo.save(comment);
        if (uni.endsWith("community")) {
            CommMessage message = commMessageRepo.findByUni(uni);
            message.getComments().add(saveComment);
            commMessageRepo.save(message);
        } else {
            UserMessage message = userMessageRepo.findByUni(uni);
            message.getComments().add(saveComment);
            userMessageRepo.save(message);
        }
    }

    public List<UserMessage> findUserMessages() {
        return userMessageRepo.findAll();
    }

    public List<Message> findMessages(User currentUser) {
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

        return messages;

    }

    public void likeMessage(String uni, User currentUser, String where) {
        if (where.equals("touser")) {
            UserMessage message = userMessageRepo.findByUni(uni);
            message.getLikes().add(currentUser);
            userMessageRepo.save(message);
        } else if (where.equals("tomessage")) {
            CommMessage message = commMessageRepo.findByUni(uni);
            message.getLikes().add(currentUser);
            commMessageRepo.save(message);
        }
    }

    public void unLikeMessage(String uni, User currentUser, String where) {
        if (where.equals("touser")) {
            UserMessage message = userMessageRepo.findByUni(uni);
            message.getLikes().remove(currentUser);
            userMessageRepo.save(message);
        } else if (where.equals("tomessage")) {
            CommMessage message = commMessageRepo.findByUni(uni);
            message.getLikes().remove(currentUser);
            commMessageRepo.save(message);
        }
    }

    public void addLikeToComment(Long id, User currentUser) {
        Comment comment = commentRepo.findById(id).get();
        comment.getLikes().add(currentUser);
        commentRepo.save(comment);

    }

    public void unLikeComment(Long id, User currentUser) {
        Comment comment = commentRepo.findById(id).get();
        comment.getLikes().remove(currentUser);
        commentRepo.save(comment);
    }
}
