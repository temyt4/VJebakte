package com.server.repos;

import com.server.domain.UserMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface UserMessageRepo extends JpaRepository<UserMessage, Long> {

    Set<UserMessage> findByAuthorId(Long id);
}
