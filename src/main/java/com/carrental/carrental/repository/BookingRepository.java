package com.carrental.carrental.repository;

import com.carrental.carrental.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByUserEmailOrderByCreatedAtDesc(String email);

    List<Booking> findAllByOrderByCreatedAtDesc();

    // Перевірка перетину бронювань (overlap):
    // існує бронювання, якщо start < existingEnd AND end > existingStart
    @Query("""
        select count(b) from Booking b
        where b.car.id = :carId
          and b.active = true
          and (:start < b.endDate and :end > b.startDate)
    """)
    long countOverlaps(Long carId, LocalDate start, LocalDate end);
}
