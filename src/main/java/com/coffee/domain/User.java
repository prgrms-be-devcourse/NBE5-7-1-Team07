package com.coffee.domain;

import jakarta.persistence.*;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(unique = true)
    private String name;

    @Setter
    private String password;

    private String role = "USER";

    @Builder
    public User(String name, String password, String role) {
        this.name = name;
        this.password = password;
        this.role = role;
    }
}
