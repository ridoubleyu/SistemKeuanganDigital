package com.example.demo.controller;

import com.example.demo.model.Tabungan;
import com.example.demo.model.User;
import com.example.demo.service.DashboardService;
import com.example.demo.model.DashboardData;
import com.example.demo.repository.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.Authentication;

import java.util.List;

@Controller
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransaksiRepository transaksiRepository;

    @Autowired
    private TabunganRepository tabunganRepository;

        @GetMapping("/")
    public String landingPage() {
        return "home";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

@GetMapping("/dashboard")
public String dashboard(
        Authentication authentication,
        Model model) {

    String email = authentication.getName();

    User user = userRepository.findByEmail(email)
            .orElse(null);

    if (user == null) {
        return "redirect:/login";
    }

    DashboardData data =
            dashboardService.getDashboardData(user.getId());

    model.addAttribute("data", data);

    model.addAttribute(
            "listTransaksi",
            transaksiRepository.findByUser_Id(user.getId())
    );

    return "dashboard";
}


}