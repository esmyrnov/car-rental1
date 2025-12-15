package com.carrental.carrental.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "cars")
@Getter
@Setter
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ===================== ОСНОВНІ ДАНІ =====================

    @NotBlank(message = "Виробник обовʼязковий")
    @Column(nullable = false, length = 50)
    private String make; // Toyota

    @NotBlank(message = "Модель обовʼязкова")
    @Column(nullable = false, length = 50)
    private String model; // Corolla

    @Min(value = 1970, message = "Рік випуску некоректний")
    @Column(nullable = false)
    private int year;

    @DecimalMin(value = "1.0", message = "Ціна повинна бути більшою за 0")
    @Column(name = "price_per_day", nullable = false)
    private double pricePerDay;

    // ===================== СТАН =====================

    @Column(name = "is_available", nullable = false)
    private boolean available = true;

    // ===================== ОПИС =====================

    @Column(length = 1000)
    private String description;

    @Column(length = 255)
    private String imageUrl;

    @NotBlank(message = "Категорія обовʼязкова")
    @Column(length = 30)
    private String category; // Economy / Comfort / SUV / Popular
}
