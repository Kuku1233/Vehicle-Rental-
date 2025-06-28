package org.vehiclerental.vehiclerentalsystem.controller;

import org.vehiclerental.vehiclerentalsystem.model.User;
import org.vehiclerental.vehiclerentalsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("user") User user, Model model) {
        if (userService.findByUsername(user.getUsername()) != null) {
            model.addAttribute("error", "Username already exists!");
            return "register";
        }


        // Hash the password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Set default role
        user.setRole("ROLE_CUSTOMER");

        userService.saveUser(user);
        return "redirect:/login";
    }
}