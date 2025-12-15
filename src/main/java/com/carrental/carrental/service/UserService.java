package com.carrental.carrental.service;

import com.carrental.carrental.dto.RegisterDto;
import com.carrental.carrental.entity.User;

import java.util.List;

public interface UserService {
    User register(RegisterDto dto);
    User findByEmailOrThrow(String email);
    List<User> findAll();
    void setAdmin(Long userId);
    void setUser(Long userId);
}
