package com.example.demo.controller;

import com.example.demo.model.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.demo.repository.UserRepository;
import org.springframework.ui.Model;





@Controller
public class AdminController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/admin")
    public String adminPage(
            Model model,
            HttpSession session
    ){

        User user =
                (User) session.getAttribute("user");

        if(user == null){
            return "redirect:/login";
        }

        if(!"ADMIN".equals(user.getRole())){
            return "redirect:/dashboard";
        }

        model.addAttribute(
                "listUser",
                userRepository.findAll()
        );

        return "admin";
    }
}