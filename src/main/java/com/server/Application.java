package com.server;

import com.server.domain.Role;
import com.server.domain.User;
import com.server.repos.UserRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;


/**
 * created by xev11
 */

@SpringBootApplication
public class Application {

//    @Bean
//    CommandLineRunner commandLineRunner(UserRepo userRepo, PasswordEncoder passwordEncoder){
//        return args->{
//            userRepo.save(new com.server.domain.User(1L, "user", passwordEncoder.encode("password"), Collections.singleton(Role.ADMIN)))
//            .subscribe(u->{
//                System.out.println(u.toString());
//            }, Throwable::printStackTrace);
//        };
//    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }


}
