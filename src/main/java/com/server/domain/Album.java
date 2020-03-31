package com.server.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "album")
public class Album {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    private String name;

    @OneToMany
    @JoinTable(name = "photos", joinColumns = @JoinColumn(name = "albumid"), inverseJoinColumns = @JoinColumn(name = "photoid"))
    private Set<Photo> photos = new HashSet<>();

    private Long userid;

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

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
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
