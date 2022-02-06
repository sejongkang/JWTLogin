package com.k3j.jwtlogin.service;

import com.k3j.jwtlogin.domain.Role;
import com.k3j.jwtlogin.domain.User;
import com.k3j.jwtlogin.repo.RoleRepo;
import com.k3j.jwtlogin.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
// UserDetailsService를 구현해서 Spring Security에서 UserServiceImple을 사용하도록 함.
// Service
public class UserServiceImpl implements UserService, UserDetailsService {

    // Autowired 대신, 생성자 주입.
    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;

    // 회원가입 엔드포인트 구현해야함.
    @Override
    public User saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
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

    // UserDetailService의 기본 함수 구현.
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        Optional<User> user = userRepo.findByUsername(username);
//        if(!user.isPresent()){
//            throw new UsernameNotFoundException("user not found");
//        }

        Optional<User> user = Optional.ofNullable(userRepo.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("no tfound")));
        // ArrayList는 가변적 크기.
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.get().getRoles().forEach(role ->
                authorities.add(new SimpleGrantedAuthority(role.getName())));
        // Entity User 클래스 조회해서 Security User 클래스로 반환.
        return new org.springframework.security.core.userdetails.User(user.get().getUsername(), user.get().getPassword(), authorities);
    }
}
