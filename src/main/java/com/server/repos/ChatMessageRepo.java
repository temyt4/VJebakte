package com.server.repos;

import com.server.domain.ChatMessage;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

/**
 * created by xev11
 */

public interface ChatMessageRepo extends ReactiveMongoRepository<ChatMessage, String> {
}
