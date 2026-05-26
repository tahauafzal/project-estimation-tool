package com.alpha.controller;

import com.alpha.model.User;
import com.alpha.model.Role;
import com.alpha.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegistrationController {
    
    @Autowired
    private UserRepository userRepository;
    
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }
    
    @PostMapping("/register")
    public String registerUser(@RequestParam String username, 
                               @RequestParam String email, 
                               @RequestParam String password,
                               Model model) {
        try {
            if (userRepository.existsByUsername(username)) {
                model.addAttribute("error", "Username already exists");
                return "register";
            }
            if (userRepository.existsByEmail(email)) {
                model.addAttribute("error", "Email already exists");
                return "register";
            }
            
            User user = new User();
            user.setUsername(username);
            user.setEmail(email);
            user.setPassword(password);
            user.setRole(Role.DEVELOPER);
            
            userRepository.save(user);
            return "redirect:/projects";
            
        } catch (Exception e) {
            model.addAttribute("error", "Registration failed: " + e.getMessage());
            return "register";
        }
    }
}
