package com.server.repos;

import com.server.domain.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

/**
 * created by xev11
 */

public interface UserRepo extends ReactiveMongoRepository<User, String> {


    Mono<User> findByUsername(String username);
}
