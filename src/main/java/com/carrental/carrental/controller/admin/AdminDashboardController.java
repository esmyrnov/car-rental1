package com.carrental.carrental.controller.admin;

import com.carrental.carrental.repository.BookingRepository;
import com.carrental.carrental.repository.CarRepository;
import com.carrental.carrental.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminDashboardController {

    private final CarRepository carRepository;
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;

    @GetMapping({"", "/", "/dashboard"})
    public String dashboard(Model model) {

        model.addAttribute("carsCount", carRepository.count());
        model.addAttribute("bookingsCount", bookingRepository.count());
        model.addAttribute("usersCount", userRepository.count());

        return "admin/dashboard";
    }
}
