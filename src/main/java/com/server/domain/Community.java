package com.server.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

/**
 * created by xev11
 */

@Entity
@Table(name = "communities")
public class Community implements Serializable {

    private static final long serialVersionUID = 7960083376527756290l;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "community_users", joinColumns = @JoinColumn(name = "comm_id"), inverseJoinColumns = @JoinColumn(name = "usr_id"))
    private Set<User> community_users = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "community_admins", joinColumns = @JoinColumn(name = "comm_id"), inverseJoinColumns = @JoinColumn(name = "usr_id"))
    private Set<User> admins = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "comtomes", joinColumns = @JoinColumn(name = "comid"), inverseJoinColumns = @JoinColumn(name = "mesid"))
    private Set<CommMessage> messages = new HashSet<>();

    public Long getId() {
        return id;
    }

    @Column(name = "avatar")
    private String avatar;

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<User> getCommunity_users() {
        return community_users;
    }

    public void setCommunity_users(Set<User> community_users) {
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

    public Set<User> getAdmins() {
        return admins;
    }

    public void setAdmins(Set<User> admins) {
        this.admins = admins;
    }

    @Override
    public String toString() {
        return "Community{" +
                "name='" + name + '\'' +
                '}';
    }
}
