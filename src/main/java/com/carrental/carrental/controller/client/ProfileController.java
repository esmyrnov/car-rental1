package com.carrental.carrental.controller.client;

import com.carrental.carrental.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class ProfileController {

    private final BookingService bookingService;

    @GetMapping("/profile")
    public String profile(@AuthenticationPrincipal UserDetails user, Model model) {
        model.addAttribute("bookings", bookingService.my(user.getUsername()));
        return "client/profile";
    }

    @PostMapping("/bookings/{id}/cancel")
    public String cancel(@PathVariable Long id, @AuthenticationPrincipal UserDetails user) {
        bookingService.cancelAsUser(id, user.getUsername());
        return "redirect:/profile";
    }
}
