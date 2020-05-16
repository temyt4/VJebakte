package com.server.repos;

import com.server.domain.Community;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

/**
 * created by xev11
 */

public interface CommRepo extends ReactiveMongoRepository<Community, String> {
    Mono<Community> findByName(String name);
}
