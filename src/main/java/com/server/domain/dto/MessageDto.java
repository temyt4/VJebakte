package com.server.domain.dto;

import com.server.domain.Comment;

import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class MessageDto {

    private Long id;
    private String text;
    private String filename;
    private String authorName;
    private Date createdDate;
    private boolean isUser;
    private Set<Comment> comments = new HashSet<>();
    private String uni;

    public MessageDto(Long id, String text, String filename, String authorName, Date createdDate, boolean isUser, Set<Comment> comments, String uni) {
        this.id = id;
        this.text = text;
        this.filename = filename;
        this.authorName = authorName;
        this.createdDate = createdDate;
        this.isUser = isUser;
        this.comments = comments;
        this.uni = uni;
    }

    public MessageDto(Long id, String text, String filename, String authorName, Date createdDate, boolean isUser, String uni) {
        this.id = id;
        this.text = text;
        this.filename = filename;
        this.authorName = authorName;
        this.createdDate = createdDate;
        this.isUser = isUser;
        this.uni = uni;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public boolean isUser() {
        return isUser;
    }

    public void setUser(boolean user) {
        isUser = user;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public void setUni(String uni) {
        this.uni = uni;
    }

    public String getUni() {
        return uni;
    }
}
