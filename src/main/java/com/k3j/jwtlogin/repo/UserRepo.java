package com.k3j.jwtlogin.repo;

import com.k3j.jwtlogin.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// JpaRepository에서 기본적인 CRUD 메소드 지원.
public interface UserRepo extends JpaRepository<User, Long> {

    // 메소드 이름과 리턴 타입으로 자동으로 쿼리 생성 지원.
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
}
