package com.carrental.carrental.controller.client;

import com.carrental.carrental.dto.SearchDto;
import com.carrental.carrental.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class CatalogController {

    private final CarService carService;

    @GetMapping({"/", "/catalog"})
    public String catalog(@ModelAttribute("s") SearchDto s, Model model) {
        model.addAttribute("cars", carService.search(s));
        return "client/catalog";
    }
}
