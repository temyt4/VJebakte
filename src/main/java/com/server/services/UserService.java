package com.server.services;

import com.server.domain.*;
import com.server.domain.dto.MessageDto;
import com.server.domain.dto.UserDto;
import com.server.repos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

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

}
