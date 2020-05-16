package com.server.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.*;

/**
 * created by xev11
 */

@Document
@Transactional
public class Community implements Serializable {

    private static final long serialVersionUID = 7960083376527756290l;

    @Id
    private String id;

    private String name;

    private Set<String> community_users = new HashSet<>();

    private Set<String> admins = new HashSet<>();

    private Set<CommMessage> messages = new HashSet<>();

    public String getId() {
        return id;
    }

    private String avatar;

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<String> getCommunity_users() {
        return community_users;
    }

    public void setCommunity_users(Set<String> community_users) {
        this.community_users = community_users;
    }

    public Set<CommMessage> getMessages() {
        return messages;
    }

    public void setMessages(Set<CommMessage> messages) {
        this.messages = messages;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Set<String> getAdmins() {
        return admins;
    }

    public void setAdmins(Set<String> admins) {
        this.admins = admins;
    }

    @Override
    public String toString() {
        return "Community{" +
                "name='" + name + '\'' +
                '}';
    }
}
