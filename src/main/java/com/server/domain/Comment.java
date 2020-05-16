package com.server.domain;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Document
public class Comment implements Serializable {

    @Id
    private String id;

    @CreatedDate
    private Date createdDate;

    private String authorname;
    private String authorid;
    private String text;
    private String filename;

    private Set<String> likes = new HashSet<>();

    public Comment() {

    }

    public Comment(Date createdDate, String authorname, String authorid, String text) {
        this.createdDate = createdDate;
        this.authorname = authorname;
        this.authorid = authorid;
        this.text = text;
    }

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

    public String getAuthorname() {
        return authorname;
    }

    public void setAuthorname(String authorname) {
        this.authorname = authorname;
    }

    public String getAuthorid() {
        return authorid;
    }

    public void setAuthorid(String authorid) {
        this.authorid = authorid;
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

    public Set<String> getLikes() {
        return likes;
    }

    public void setLikes(Set<String> likes) {
        this.likes = likes;
    }

    public boolean meLiked(User user) {
        return likes.contains(user);
    }

    public String getStringTime(){
        return getCreatedDate().toString();
    }
}
