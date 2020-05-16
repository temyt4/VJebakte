package com.server.repos;

import com.server.domain.Comment;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface CommentRepo extends ReactiveMongoRepository<Comment, String> {
}
