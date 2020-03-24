package com.server.repos;

import com.server.domain.UserMessage;
import com.server.domain.dto.MessageDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

/**
 * created by xev11
 */

public interface UserMessageRepo extends JpaRepository<UserMessage, Long> {

    Set<UserMessage> findByAuthorId(Long id);

    @Query("select new com.server.domain.dto.MessageDto(" +
            "m.text," +
            "m.filename," +
            "m.authorName," +
            "m.createdDate," +
            " true) " +
            "from UserMessage m where m.authorId =:id")
    Set<MessageDto> findDtoByAuthorId(Long id);
}
