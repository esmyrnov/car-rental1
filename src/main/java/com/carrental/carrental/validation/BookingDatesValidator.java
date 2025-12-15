package com.carrental.carrental.validation;

import com.carrental.carrental.dto.BookingCreateDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;

public class BookingDatesValidator implements ConstraintValidator<BookingDates, BookingCreateDto> {

    @Override
    public boolean isValid(BookingCreateDto dto, ConstraintValidatorContext ctx) {
        if (dto == null || dto.getStartDate() == null || dto.getEndDate() == null) return true;

        LocalDate start = dto.getStartDate();
        LocalDate end = dto.getEndDate();

        boolean ok = true;

        if (start.isBefore(LocalDate.now())) {
            ok = false;
            ctx.disableDefaultConstraintViolation();
            ctx.buildConstraintViolationWithTemplate("Дата початку не може бути в минулому")
                    .addPropertyNode("startDate").addConstraintViolation();
        }

        if (!end.isAfter(start)) {
            ok = false;
            ctx.disableDefaultConstraintViolation();
            ctx.buildConstraintViolationWithTemplate("Дата завершення має бути пізніше дати початку")
                    .addPropertyNode("endDate").addConstraintViolation();
        }

        return ok;
    }
}
