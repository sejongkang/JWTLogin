package com.k3j.jwtlogin.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.Collection;

import static javax.persistence.FetchType.EAGER;
import static javax.persistence.GenerationType.AUTO;

@Entity //JPA에서 관리할 객체 선언.
@Data   //Lombok의 Getter,Setter,ToString,~Constructor 등 묶음
//@NoArgsConstructor  //Argument 없는 생성자 생성
//@AllArgsConstructor //모든 Argument 포함한 생성자 생성
// UserDatils 클래스와 분리해서 관리하는게 나음.
public class User {

    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;

    @Column(length = 100, nullable = false, unique = true)
    private String email;

    private String username;

    @Column(length = 100, nullable = false)
    private String password;

    @ManyToMany(fetch = EAGER)
    private Collection<Role> roles = new ArrayList<>();
}
