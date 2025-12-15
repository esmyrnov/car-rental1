package com.carrental.carrental.controller.client;

import com.carrental.carrental.dto.BookingCreateDto;
import com.carrental.carrental.service.BookingService;
import com.carrental.carrental.service.CarService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class BookingController {

    private final CarService carService;
    private final BookingService bookingService;

    @GetMapping("/booking/{carId}")
    public String form(@PathVariable Long carId, Model model) {
        model.addAttribute("car", carService.get(carId));
        model.addAttribute("form", new BookingCreateDto());
        return "client/booking";
    }

    @PostMapping("/booking/{carId}")
    public String submit(@PathVariable Long carId,
                         @Valid @ModelAttribute("form") BookingCreateDto form,
                         BindingResult br,
                         @AuthenticationPrincipal UserDetails user,
                         Model model) {

        model.addAttribute("car", carService.get(carId));

        if (br.hasErrors()) return "client/booking";

        try {
            bookingService.create(carId, user.getUsername(), form);
        } catch (Exception ex) {
            model.addAttribute("error", ex.getMessage());
            return "client/booking";
        }

        return "redirect:/profile";
    }
}
