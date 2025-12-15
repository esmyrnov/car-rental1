package com.carrental.carrental.service.impl;

import com.carrental.carrental.dto.BookingCreateDto;
import com.carrental.carrental.entity.Booking;
import com.carrental.carrental.entity.Car;
import com.carrental.carrental.entity.User;
import com.carrental.carrental.repository.BookingRepository;
import com.carrental.carrental.repository.CarRepository;
import com.carrental.carrental.repository.UserRepository;
import com.carrental.carrental.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepo;
    private final CarRepository carRepo;
    private final UserRepository userRepo;

    @Override
    public Booking create(Long carId, String userEmail, BookingCreateDto dto) {
        Car car = carRepo.findById(carId).orElseThrow();
        if (!car.isAvailable()) throw new IllegalStateException("Авто недоступне");

        long overlaps = bookingRepo.countOverlaps(carId, dto.getStartDate(), dto.getEndDate());
        if (overlaps > 0) throw new IllegalStateException("Авто вже заброньоване на ці дати");

        User user = userRepo.findByEmail(userEmail).orElseThrow();

        Booking b = new Booking();
        b.setCar(car);
        b.setUser(user);
        b.setStartDate(dto.getStartDate());
        b.setEndDate(dto.getEndDate());
        b.setActive(true);

        return bookingRepo.save(b);
    }

    @Override
    public List<Booking> my(String userEmail) {
        return bookingRepo.findByUserEmailOrderByCreatedAtDesc(userEmail);
    }

    @Override
    public List<Booking> all() {
        return bookingRepo.findAllByOrderByCreatedAtDesc();
    }

    @Override
    public void cancelAsUser(Long bookingId, String userEmail) {
        Booking b = bookingRepo.findById(bookingId).orElseThrow();
        if (!b.getUser().getEmail().equals(userEmail)) throw new SecurityException("Forbidden");
        b.setActive(false);
        b.setCancelledAt(LocalDateTime.now());
        bookingRepo.save(b);
    }

    @Override
    public void cancelAsAdmin(Long bookingId) {
        Booking b = bookingRepo.findById(bookingId).orElseThrow();
        b.setActive(false);
        b.setCancelledAt(LocalDateTime.now());
        bookingRepo.save(b);
    }

    @Override
    public long countActive() {
        return bookingRepo.findAll().stream().filter(Booking::isActive).count();
    }
}
