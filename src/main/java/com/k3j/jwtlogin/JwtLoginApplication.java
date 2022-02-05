package com.k3j.jwtlogin;

import com.k3j.jwtlogin.domain.Role;
import com.k3j.jwtlogin.domain.User;
import com.k3j.jwtlogin.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;

@SpringBootApplication
public class JwtLoginApplication {

    public static void main(String[] args) {
        SpringApplication.run(JwtLoginApplication.class, args);
    }

    @Bean
    CommandLineRunner run(UserService userService){
        return args -> {
            userService.saveRole(new Role(null, "ROLE_USER"));
            userService.saveRole(new Role(null, "ROLE_MANAGER"));
            userService.saveRole(new Role(null, "ROLE_ADMIN"));
            userService.saveRole(new Role(null, "ROLE_SUPER_ADMIN"));

            userService.saveUser(new User(null, "john@gmail.com", "john", "1234", new ArrayList<>()));
            userService.saveUser(new User(null, "john2@gmail.com", "john2", "1234", new ArrayList<>()));
            userService.saveUser(new User(null, "john3@gmail.com", "john3", "1234", new ArrayList<>()));
            userService.saveUser(new User(null, "john4@gmail.com", "john4", "1234", new ArrayList<>()));

            userService.addRoleToUser("john@gmail.com", "ROLE_USER");
            userService.addRoleToUser("john@gmail.com", "ROLE_MANAGER");
            userService.addRoleToUser("john2@gmail.com", "ROLE_USER");
            userService.addRoleToUser("john3@gmail.com", "ROLE_ADMIN");
            userService.addRoleToUser("john4@gmail.com", "ROLE_ADMIN");
            userService.addRoleToUser("john4@gmail.com", "ROLE_USER");
        };
    }

}
