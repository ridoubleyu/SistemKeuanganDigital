package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.servlet.http.HttpSession;

import java.util.Optional;

@Controller
public class AuthWebController {

    @Autowired
    private UserRepository userRepository;

    // LOGIN


    // REGISTER
    @PostMapping("/register")
    public String register(
            @RequestParam String nama,
            @RequestParam String email,
            @RequestParam String password
    ) {

        User user = new User();

        user.setNama(nama);
        user.setEmail(email);
        user.setPassword(password);

        user.setRole("USER");

        userRepository.save(user);

        return "redirect:/login";
    }
}