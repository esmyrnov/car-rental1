package com.carrental.carrental.controller.admin;

import com.carrental.carrental.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/users")
@RequiredArgsConstructor
public class AdminUserController {

    private final UserService userService;

    @GetMapping
    public String users(Model model) {
        model.addAttribute("users", userService.findAll());
        return "admin/users";
    }

    @PostMapping("/{id}/make-admin")
    public String makeAdmin(@PathVariable Long id) {
        userService.setAdmin(id);
        return "redirect:/admin/users";
    }

    @PostMapping("/{id}/make-user")
    public String makeUser(@PathVariable Long id) {
        userService.setUser(id);
        return "redirect:/admin/users";
    }
}
