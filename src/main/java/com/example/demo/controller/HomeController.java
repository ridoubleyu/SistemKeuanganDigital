package com.example.demo.controller;

import com.example.demo.model.Transaksi;
import com.example.demo.repository.TransaksiRepository;
import com.example.demo.model.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private TransaksiRepository transaksiRepository;

    @GetMapping("/")
    public String home() {
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
            HttpSession session) {

        User user = (User) session.getAttribute("user");

        if(user == null){
            return "redirect:/login";
        }

        List<Transaksi> transaksiList =
                transaksiRepository.findByUser(user);

        double pemasukan = 0;
        double pengeluaran = 0;

        for (Transaksi t : transaksiList) {

            if ("Pemasukan".equals(t.getJenis())) {
                pemasukan += t.getJumlah();
            } else {
                pengeluaran += t.getJumlah();
            }
        }

        double saldo = pemasukan - pengeluaran;

        model.addAttribute("saldo", saldo);
        model.addAttribute("pemasukan", pemasukan);
        model.addAttribute("pengeluaran", pengeluaran);
        model.addAttribute("listTransaksi", transaksiList);

        return "dashboard";
    }
}