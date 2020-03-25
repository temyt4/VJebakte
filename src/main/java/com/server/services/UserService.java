package com.server.services;

import com.server.domain.ChatMessage;
import com.server.domain.Community;
import com.server.domain.User;
import com.server.domain.UserMessage;
import com.server.domain.dto.MessageDto;
import com.server.domain.dto.UserDto;
import com.server.repos.ChatMessageRepo;
import com.server.repos.CommRepo;
import com.server.repos.UserMessageRepo;
import com.server.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
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
        Community save = commRepo.save(community);
        user.getCommunities().add(save);
        userRepo.save(user);
    }

}
