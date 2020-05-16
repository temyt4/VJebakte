package com.server.domain;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * created by xev11
 */

@Document
public class CommMessage implements Message, Serializable, Comparable<CommMessage> {

    private static final long serialVersionUID = 7565783440143452812L;

    @Id
    private String id;

    private String text;

    private String filename;

    @CreatedDate
    private Date createdDate = new Date();

    private String authorName;

    @DBRef
    private Set<Comment> comments = new HashSet<>();

    private String uni = "roflan" + getCreatedDate().getTime() + "community";

    @DBRef
    private Set<User> likes = new HashSet<>();

    public CommMessage() {
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    @Override
    public int compareTo(CommMessage o) {
        return getCreatedDate().compareTo(o.getCreatedDate());
    }

    public String getUni() {
        return uni;
    }

    @Override
    public boolean isUser() {
        return false;
    }

    public void setUni(String uni) {
        this.uni = uni;
    }

    public Set<User> getLikes() {
        return likes;
    }

    public void setLikes(Set<User> likes) {
        this.likes = likes;
    }

    @Override
    public boolean meLiked(User user) {
        return likes.contains(user);
    }

    @Override
    public String getStringTime() {
        return getCreatedDate().toString();
    }
}
