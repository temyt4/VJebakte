package com.server.repos;

import com.server.domain.CommMessage;
import com.server.domain.dto.MessageDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

/**
 * created by xev11
 */

public interface CommMessageRepo extends JpaRepository<CommMessage, Long> {

    @Query("select new com.server.domain.dto.MessageDto(" +
            "m.id, " +
            "m.text," +
            "m.filename," +
            "m.authorName," +
            "m.createdDate," +
            " false," +
            "m.uni) " +
            "from CommMessage m where m.authorName =:name")
    Set<MessageDto> findDtoByAuthorName(String name);

    CommMessage findByUni(String uni);

}
