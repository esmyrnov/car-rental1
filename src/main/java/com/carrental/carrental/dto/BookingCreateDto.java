package com.carrental.carrental.dto;

import com.carrental.carrental.validation.BookingDates;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
@BookingDates
public class BookingCreateDto {

    @NotNull(message = "Дата початку обовʼязкова")
    private LocalDate startDate;

    @NotNull(message = "Дата завершення обовʼязкова")
    private LocalDate endDate;
}
