package com.server.domain;

import java.util.Date;
import java.util.Set;

public interface Message {

    Long getId();

    String getText();

    String getFilename();

    Date getCreatedDate();

    String getAuthorName();

    Set<Comment> getComments();

    String getUni();

    boolean isUser();

    void setComments(Set<Comment> comments);

    Set<User> getLikes();

    boolean meLiked(User user);
}
