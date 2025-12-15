package com.carrental.carrental.service.impl;

import com.carrental.carrental.dto.RegisterDto;
import com.carrental.carrental.entity.Role;
import com.carrental.carrental.entity.User;
import com.carrental.carrental.repository.RoleRepository;
import com.carrental.carrental.repository.UserRepository;
import com.carrental.carrental.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepo;
    private final RoleRepository roleRepo;
    private final PasswordEncoder encoder;

    @Override
    public User register(RegisterDto dto) {
        userRepo.findByEmail(dto.getEmail()).ifPresent(u -> {
            throw new IllegalArgumentException("Email вже зареєстровано");
        });

        Role userRole = roleRepo.findByName("ROLE_USER").orElseThrow();

        User u = new User();
        u.setFullName(dto.getFullName());
        u.setEmail(dto.getEmail());
        u.setPhone(dto.getPhone());
        u.setPassword(encoder.encode(dto.getPassword()));
        u.getRoles().add(userRole);

        return userRepo.save(u);
    }

    @Override
    public User findByEmailOrThrow(String email) {
        return userRepo.findByEmail(email).orElseThrow();
    }

    @Override
    public List<User> findAll() {
        return userRepo.findAll();
    }

    @Override
    public void setAdmin(Long userId) {
        User u = userRepo.findById(userId).orElseThrow();
        Role admin = roleRepo.findByName("ROLE_ADMIN").orElseThrow();
        u.getRoles().add(admin);
        userRepo.save(u);
    }

    @Override
    public void setUser(Long userId) {
        User u = userRepo.findById(userId).orElseThrow();
        u.getRoles().removeIf(r -> r.getName().equals("ROLE_ADMIN"));
        userRepo.save(u);
    }
}
