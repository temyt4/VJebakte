package com.server.domain;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.transaction.annotation.Transactional;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * created by xev11
 */

@Document
@Transactional
public class UserMessage implements Message, Serializable {

    private static final long serialVersionUID = 8650170500103969988L;

    @Id
    private String id;

    @CreatedDate
    private Date createdDate = new Date();


    private String text;

    private String filename;


    private String authorId;


    private String authorName;

    @DBRef
    private Set<Comment> comments = new HashSet<>();

    private String uni = "roflan" + getCreatedDate().getTime()+"user";

    @DBRef
    private Set<User> likes = new HashSet<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
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

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
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

    public String getUni() {
        return uni;
    }

    @Override
    public boolean isUser() {
        return true;
    }

    public void setUni(String uni) {
        this.uni = uni;
    }

    @Override
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
