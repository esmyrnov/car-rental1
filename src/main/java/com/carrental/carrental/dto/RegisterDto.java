package com.carrental.carrental.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RegisterDto {

    @NotBlank(message = "ПІБ обовʼязкове")
    private String fullName;

    @Email(message = "Некоректний email")
    @NotBlank(message = "Email обовʼязковий")
    private String email;

    @NotBlank(message = "Телефон обовʼязковий")
    private String phone;

    @Size(min = 6, message = "Пароль мінімум 6 символів")
    @NotBlank(message = "Пароль обовʼязковий")
    private String password;
}
