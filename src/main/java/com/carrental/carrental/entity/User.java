package com.carrental.carrental.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter @Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Імʼя та прізвище обовʼязкові")
    @Column(name = "full_name", nullable = false, length = 120)
    private String fullName;

    @Email(message = "Некоректний email")
    @NotBlank(message = "Email обовʼязковий")
    @Column(unique = true, nullable = false, length = 100)
    private String email;

    @NotBlank(message = "Пароль обовʼязковий")
    @Column(nullable = false, length = 255)
    private String password;

    @NotBlank(message = "Телефон обовʼязковий")
    @Column(nullable = false, length = 20)
    private String phone;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();
}
