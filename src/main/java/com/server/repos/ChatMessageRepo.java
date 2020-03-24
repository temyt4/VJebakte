package com.server.repos;

import com.server.domain.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * created by xev11
 */

public interface ChatMessageRepo extends JpaRepository<ChatMessage, Long> {
}
