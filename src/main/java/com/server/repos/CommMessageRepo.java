package com.server.repos;

import com.server.domain.CommMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommMessageRepo extends JpaRepository<CommMessage, Long> {
}
