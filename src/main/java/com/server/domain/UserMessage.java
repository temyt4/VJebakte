package com.server.domain;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * created by xev11
 */

@Entity
@Table(name = "usrmessage")
@Transactional
public class UserMessage implements Message, Serializable {

    private static final long serialVersionUID = 8650170500103969988L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreatedDate
    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name = "createddate")
    private Date createdDate = new Date();


    private String text;

    private String filename;

    @Column(name = "authorid")
    private Long authorId;

    @Column(name = "authorname")
    private String authorName;

    @OneToMany
    @JoinTable(name = "comments_user", joinColumns = @JoinColumn(name = "usrmesid"), inverseJoinColumns = @JoinColumn(name = "commentid"))
    private Set<Comment> comments = new HashSet<>();

    private String uni = "roflan" + getCreatedDate().getTime();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
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
}
