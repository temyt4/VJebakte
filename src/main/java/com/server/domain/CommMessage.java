package com.server.domain;

import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * created by xev11
 */

@Entity
@Table(name = "commessage")
public class CommMessage implements Message, Serializable, Comparable<CommMessage> {

    private static final long serialVersionUID = 7565783440143452812L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text;

    private String filename;

    @CreatedDate
    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name = "createddate")
    private Date createdDate = new Date();

    @Column(name = "authorname")
    private String authorName;

    @OneToMany
    @JoinTable(name = "comments_comm", joinColumns = @JoinColumn(name = "commmesid"), inverseJoinColumns = @JoinColumn(name = "commentid"))
    private Set<Comment> comments = new HashSet<>();

    private String uni = "roflan" + getCreatedDate().getTime() + "community";

    public CommMessage() {
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
}
