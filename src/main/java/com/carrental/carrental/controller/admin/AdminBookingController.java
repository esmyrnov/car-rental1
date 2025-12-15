package com.carrental.carrental.controller.admin;

import com.carrental.carrental.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/bookings")
@RequiredArgsConstructor
public class AdminBookingController {

    private final BookingService bookingService;

    @GetMapping
    public String bookings(Model model) {
        model.addAttribute("bookings", bookingService.all());
        return "admin/bookings";
    }

    @PostMapping("/{id}/cancel")
    public String cancel(@PathVariable Long id) {
        bookingService.cancelAsAdmin(id);
        return "redirect:/admin/bookings";
    }
}
