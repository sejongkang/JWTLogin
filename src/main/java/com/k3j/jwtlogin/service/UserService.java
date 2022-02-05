package com.k3j.jwtlogin.service;

import com.k3j.jwtlogin.domain.Role;
import com.k3j.jwtlogin.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User saveUser(User user);
    Role saveRole(Role role);
    void addRoleToUser(String email, String roleName);
    Optional<User> getUser(String email);
    List<User> getUsers();
}
