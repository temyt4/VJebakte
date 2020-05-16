package com.server.repos;

import com.server.domain.Album;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface AlbumRepo extends ReactiveMongoRepository<Album, String> {

    Mono<Album> findByName(String albumname);
}
