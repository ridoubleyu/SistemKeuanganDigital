package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.repository.TransaksiRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LaporanController {

    @Autowired
    private TransaksiRepository transaksiRepository;
    @GetMapping("/laporan")
    public String laporan(
            Model model,
            HttpSession session
    ) {

        User user = (User) session.getAttribute("user");

        if(user == null){
            return "redirect:/login";
        }

        model.addAttribute(
                "listTransaksi",
                transaksiRepository.findByUser(user)
        );

        return "laporan";
    }
}