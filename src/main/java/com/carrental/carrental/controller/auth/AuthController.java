package com.carrental.carrental.controller.auth;

import com.carrental.carrental.dto.RegisterDto;
import com.carrental.carrental.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @GetMapping("/login")
    public String login() { return "auth/login"; }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("form", new RegisterDto());
        return "auth/register";
    }

    @PostMapping("/register")
    public String registerPost(@Valid @ModelAttribute("form") RegisterDto form,
                               BindingResult br,
                               Model model) {
        if (br.hasErrors()) return "auth/register";
        try {
            userService.register(form);
        } catch (IllegalArgumentException ex) {
            br.rejectValue("email", "exists", ex.getMessage());
            return "auth/register";
        }
        return "redirect:/auth/login";
    }
}
