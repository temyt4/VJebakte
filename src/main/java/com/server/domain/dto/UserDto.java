package com.server.domain.dto;

import com.server.domain.Community;
import com.server.domain.Role;
import com.server.domain.User;
import com.server.domain.UserMessage;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.Set;

public class UserDto {
    private Long id;
    private String username;
    private String password;
    private Date birthdate;
    private String user_avatar;
    private String email;
    private Set<Role> roles;
    private Set<User> friends;
    private Set<Community> communities;
    private Set<UserMessage> messages;

    public UserDto(User u) {
        this.id = u.getId();
        this.username = u.getUsername();
        this.password = u.getPassword();
        this.birthdate = u.getBirthdate();
        this.user_avatar = u.getUser_avatar();
        this.email = u.getEmail();
        this.roles = u.getRoles();
        this.friends = u.getFriends();
        this.communities = u.getCommunities();
        this.messages = u.getMessages();
    }

    public UserDto() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public String getUser_avatar() {
        return user_avatar;
    }

    public void setUser_avatar(String user_avatar) {
        this.user_avatar = user_avatar;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Set<User> getFriends() {
        return friends;
    }

    public void setFriends(Set<User> friends) {
        this.friends = friends;
    }

    public Set<Community> getCommunities() {
        return communities;
    }

    public void setCommunities(Set<Community> communities) {
        this.communities = communities;
    }

    public Set<UserMessage> getMessages() {
        return messages;
    }

    public void setMessages(Set<UserMessage> messages) {
        this.messages = messages;
    }
}
