package com.carrental.carrental.service;

import com.carrental.carrental.dto.BookingCreateDto;
import com.carrental.carrental.entity.Booking;

import java.util.List;

public interface BookingService {
    Booking create(Long carId, String userEmail, BookingCreateDto dto);
    List<Booking> my(String userEmail);
    List<Booking> all();
    void cancelAsUser(Long bookingId, String userEmail);
    void cancelAsAdmin(Long bookingId);
    long countActive();
}
