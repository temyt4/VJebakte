package com.server.repos;

import com.server.domain.UserMessage;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Set;

/**
 * created by xev11
 */

public interface UserMessageRepo extends ReactiveMongoRepository<UserMessage, String> {

    Flux<UserMessage> findByAuthorId(String id);


    Mono<UserMessage> findByUni(String uni);
}
