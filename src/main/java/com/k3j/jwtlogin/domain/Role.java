package com.k3j.jwtlogin.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import static javax.persistence.GenerationType.AUTO;

@Entity
@Data
// Spring Security에서 사용하는 Granted 어쩌구 클래스와 분리해서 사용. 캐스트해서 사용.
public class Role {

    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;

    private String name;
}
