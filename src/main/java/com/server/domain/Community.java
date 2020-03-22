package com.server.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "communities")
public class Community implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "community_subers", joinColumns = @JoinColumn(name = "comm_id"), inverseJoinColumns = @JoinColumn(name = "usr_id"))
    private Set<User> community_users = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "comtomes", joinColumns = @JoinColumn(name = "comid"), inverseJoinColumns = @JoinColumn(name = "mesid"))
    private Set<CommMessage> messages = new HashSet<>();

    public Long getId() {
        return id;
    }

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
}
