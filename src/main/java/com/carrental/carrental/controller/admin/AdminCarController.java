package com.carrental.carrental.controller.admin;

import com.carrental.carrental.entity.Car;
import com.carrental.carrental.service.CarService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/cars")
@RequiredArgsConstructor
public class AdminCarController {

    private final CarService carService;

    @GetMapping
    public String cars(Model model) {
        model.addAttribute("cars", carService.all());
        model.addAttribute("car", new Car());
        return "admin/cars";
    }

    @PostMapping("/add")
    public String add(@Valid @ModelAttribute("car") Car car, BindingResult br, Model model) {
        if (br.hasErrors()) {
            model.addAttribute("cars", carService.all());
            return "admin/cars";
        }
        carService.save(car);
        return "redirect:/admin/cars";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        carService.delete(id);
        return "redirect:/admin/cars";
    }

    @PostMapping("/{id}/toggle")
    public String toggle(@PathVariable Long id) {
        Car c = carService.get(id);
        c.setAvailable(!c.isAvailable());
        carService.save(c);
        return "redirect:/admin/cars";
    }
    @GetMapping("/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("car", carService.get(id));
        return "admin/car-edit";
    }

    @PostMapping("/{id}/edit")
    public String update(
            @PathVariable Long id,
            @Valid @ModelAttribute("car") Car car,
            BindingResult br
    ) {
        if (br.hasErrors()) {
            return "admin/car-edit";
        }
        car.setId(id);
        carService.save(car);
        return "redirect:/admin/cars";
    }

}
