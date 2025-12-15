package com.carrental.carrental.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = BookingDatesValidator.class)
@Documented
public @interface BookingDates {
    String message() default "Некоректні дати бронювання";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
