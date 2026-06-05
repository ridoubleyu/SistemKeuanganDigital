package com.example.demo.controller;

import com.example.demo.model.Tabungan;
import com.example.demo.model.User;
import com.example.demo.repository.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Controller
public class DashboardController {

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
            Model model,
            HttpSession session
    ){

        User user = (User) session.getAttribute("user");

        if(user == null){
            return "redirect:/login";
        }

        Double pemasukan =
                transaksiRepository.totalPemasukan(user.getId());

        Double pengeluaran =
                transaksiRepository.totalPengeluaran(user.getId());

        Double saldo =
                pemasukan - pengeluaran;

        Double totalTabungan = 0.0;

        for(Tabungan t :
                tabunganRepository.findByUserId(user.getId())){

            totalTabungan += t.getJumlahTerkumpul();
        }

        model.addAttribute("pemasukan", pemasukan);
        model.addAttribute("pengeluaran", pengeluaran);
        model.addAttribute("saldo", saldo);
        model.addAttribute("totalTabungan", totalTabungan);

        return "dashboard";
    }
}