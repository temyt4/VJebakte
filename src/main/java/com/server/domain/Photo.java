package com.server.domain;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


@Document
public class Photo implements Serializable {

    @Id
    private Long id;


    private String name = new String();

    private Long albumid;

    @DBRef
    private Set<Comment> comments = new HashSet<>();

    @DBRef
    private Set<User> likes = new HashSet<>();

    @CreatedDate
    private Date createdDate = new Date();

    private String uni = "photo" + createdDate.getTime();

    public Photo() {
    }

    public Photo(String name) {
        this.name = name;
    }


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

    public Long getAlbumid() {
        return albumid;
    }

    public void setAlbumId(Long albumid) {
        this.albumid = albumid;
    }

    public String getUni() {
        return uni;
    }

    public void setUni(String uni) {
        this.uni = uni;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }


    public Set<User> getLikes() {
        return likes;
    }

    public void setLikes(Set<User> likes) {
        this.likes = likes;
    }

    public boolean meLiked(User user){
        return likes.contains(user);
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}
