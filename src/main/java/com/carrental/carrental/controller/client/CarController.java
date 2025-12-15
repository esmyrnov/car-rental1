package com.carrental.carrental.controller.client;

import com.carrental.carrental.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class CarController {

    private final CarService carService;

    @GetMapping("/cars/{id}")
    public String details(@PathVariable Long id, Model model) {
        model.addAttribute("car", carService.get(id));
        return "client/car";
    }
}
