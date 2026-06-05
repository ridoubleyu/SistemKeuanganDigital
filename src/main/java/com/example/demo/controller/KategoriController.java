package com.example.demo.controller;

import com.example.demo.model.Kategori;
import com.example.demo.model.Transaksi;
import com.example.demo.model.User;
import com.example.demo.repository.KategoriRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import java.time.LocalDate;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;

@Controller
public class KategoriController {

    @Autowired
    private KategoriRepository kategoriRepository;

    @GetMapping("/kategori")
    public String kategoriPage(
            Model model,
            HttpSession session){

        User user =
                (User) session.getAttribute("user");

        model.addAttribute(
                "listKategori",
                kategoriRepository.findByUserId(user.getId())
        );

        return "kategori";
    }

    @PostMapping("/kategori")
    public String tambahKategori(

            @RequestParam String nama,
            HttpSession session){

        User user =
                (User) session.getAttribute("user");

        Kategori kategori = new Kategori();

        kategori.setNama(nama);
        kategori.setUserId(user.getId());

        kategoriRepository.save(kategori);

        return "redirect:/kategori";
    }
}