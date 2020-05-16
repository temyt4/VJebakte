package com.server.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Document
public class Album implements Serializable {

    @Id
    Long id;

    private String name;

    @DBRef
    private Set<Photo> photos = new HashSet<>();

    private String userid;

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

    public Set<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(Set<Photo> photos) {
        this.photos = photos;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getLastPhoto() {
        if (!getPhotos().isEmpty()) {
            return new ArrayList<>(getPhotos()).get(getPhotos().size() - 1).getName();
        } else {
            return "default.jpg";
        }
    }
}
