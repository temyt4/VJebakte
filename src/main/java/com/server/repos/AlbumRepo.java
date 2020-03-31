package com.server.repos;

import com.server.domain.Album;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlbumRepo extends JpaRepository<Album, Long> {
    Album findByName(String albumname);
}
