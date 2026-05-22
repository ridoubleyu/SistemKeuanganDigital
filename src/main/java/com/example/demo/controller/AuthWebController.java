package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class AuthWebController {

    @Autowired
    private UserRepository userRepository;

    // LOGIN
    @PostMapping("/login")
    public String login(
            @RequestParam String email,
            @RequestParam String password
    ) {

        Optional<User> userOpt = userRepository.findByEmail(email);

        if(userOpt.isPresent()) {

            User user = userOpt.get();

            if(user.getPassword().equals(password)) {
                return "redirect:/dashboard";
            }
        }

        return "redirect:/login";
    }

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

        userRepository.save(user);

        return "redirect:/login";
    }
}