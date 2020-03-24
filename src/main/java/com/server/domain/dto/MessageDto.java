package com.server.domain.dto;

import java.util.Date;

public class MessageDto {
    private String text;
    private String filename;
    private String authorName;
    private Date createdDate;
    private boolean isUser;

    public MessageDto(String text, String filename, String authorName, Date createdDate, boolean isUser) {
        this.text = text;
        this.filename = filename;
        this.authorName = authorName;
        this.createdDate = createdDate;
        this.isUser = isUser;
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


}
