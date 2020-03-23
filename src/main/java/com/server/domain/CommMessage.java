package com.server.domain;

import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "commessage")
public class CommMessage implements Serializable, Comparable<CommMessage> {

    private static final long serialVersionUID = 7565783440143452812l;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text;

    private String filename;

    @CreatedDate
    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name= "createddate")
    private Date createdDate;

    @Column(name = "authorname")
    private String authorName;

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

    @Override
    public int compareTo(CommMessage o) {
        return getCreatedDate().compareTo(o.getCreatedDate());
    }
}
