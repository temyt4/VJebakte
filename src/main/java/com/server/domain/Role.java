package com.server.domain;
import org.springframework.security.core.GrantedAuthority;

/**
 * created by xev11
 */

public enum Role implements GrantedAuthority {

    USER, ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}
