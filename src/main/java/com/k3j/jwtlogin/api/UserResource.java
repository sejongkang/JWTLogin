package com.k3j.jwtlogin.api;

import com.k3j.jwtlogin.domain.User;
import com.k3j.jwtlogin.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserResource {

    private final UserService userService;

    @GetMapping("/users")
    public ResponseEntity<List<User>>getUsers() {
        return ResponseEntity.ok().body(userService.getUsers());
    }
}
