package com.server.repos;

import com.server.domain.Community;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * created by xev11
 */

public interface CommRepo extends JpaRepository<Community, Long> {
    Community findByName(String name);
}
