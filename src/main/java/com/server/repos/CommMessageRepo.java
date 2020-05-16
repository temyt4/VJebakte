package com.server.repos;

import com.server.domain.CommMessage;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Set;

/**
 * created by xev11
 */

public interface CommMessageRepo extends ReactiveMongoRepository<CommMessage, String> {


    Mono<CommMessage> findByUni(String uni);

    Flux<CommMessage> findByAuthorName(String name);

}
