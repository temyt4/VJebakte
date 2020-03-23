package com.server.repos;

import com.server.domain.User;
import com.server.domain.dto.UserDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UserRepo extends JpaRepository<User, Long> {

    @Query(value = "select new com.server.domain.dto.UserDto(u.id, u.username, u.password, u.birthdate, u.user_avatar, u.email, u.roles, u.friends, u.communities, u.messages) " +
            "from User u left join u.roles r left join u.friends f left join u.communities c left join u.messages m " +
            "where u.username =:username")
    UserDto findDtoByUsername(String username);

    User findByUsername(String username);
}
