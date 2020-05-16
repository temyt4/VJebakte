package com.server.domain.dto;

import com.server.domain.Community;
import com.server.domain.Role;
import com.server.domain.User;
import com.server.domain.UserMessage;

import java.util.Date;
import java.util.Objects;
import java.util.Set;

/**
 * created by xev11
 */

public class UserDto {
    private String id;
    private String username;
    private String password;
    private Date birthdate;
    private String user_avatar;
    private String email;
    private Set<Role> roles;
    private Set<String> friends;
    private Set<String> communities;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public Set<String> getFriends() {
        return friends;
    }

    public void setFriends(Set<String> friends) {
        this.friends = friends;
    }

    public Set<String> getCommunities() {
        return communities;
    }

    public void setCommunities(Set<String> communities) {
        this.communities = communities;
    }

    public Set<UserMessage> getMessages() {
        return messages;
    }

    public void setMessages(Set<UserMessage> messages) {
        this.messages = messages;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDto userDto = (UserDto) o;
        return Objects.equals(id, userDto.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
