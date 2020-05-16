package com.server.repos;

import com.server.domain.Photo;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface PhotoRepo extends ReactiveMongoRepository<Photo, String> {
    Mono<Photo> findByName(String name);

    Mono<Photo> findByUni(String name);
}
