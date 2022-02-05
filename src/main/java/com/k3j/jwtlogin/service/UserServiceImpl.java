package com.k3j.jwtlogin.service;

import com.k3j.jwtlogin.domain.Role;
import com.k3j.jwtlogin.domain.User;
import com.k3j.jwtlogin.repo.RoleRepo;
import com.k3j.jwtlogin.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    // Autowired 대신, 생성자 주입.
    private final UserRepo userRepo;
    private final RoleRepo roleRepo;

    @Override
    public User saveUser(User user) {
        return userRepo.save(user);
    }

    @Override
    public Role saveRole(Role role) {
        return roleRepo.save(role);
    }

    @Override
    public void addRoleToUser(String email, String roleName) {
        Optional<User> user = userRepo.findByEmail(email);
        Optional<Role> role = roleRepo.findByName(roleName);
        if (user.isPresent() && role.isPresent()){
            user.get().getRoles().add(role.get());
        }
    }

    @Override
    public Optional<User> getUser(String email) {
        return userRepo.findByEmail(email);
    }

    @Override
    public List<User> getUsers() {
        return userRepo.findAll();
    }
}
