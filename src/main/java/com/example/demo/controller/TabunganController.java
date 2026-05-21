package com.example.demo.controller;

import com.example.demo.model.Tabungan;
import com.example.demo.repository.TabunganRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class TabunganController {

    @Autowired
    private TabunganRepository tabunganRepository;

    @GetMapping("/tabungan")
    public String tabunganPage(Model model){

        model.addAttribute(
                "listTabungan",
                tabunganRepository.findAll()
        );

        return "tabungan";
    }

   @PostMapping("/tabungan/tambah/{id}")
public String tambahUangTabungan(

        @PathVariable Long id,
        @RequestParam Double nominal
){

    Tabungan tabungan =
            tabunganRepository.findById(id).orElse(null);

    if(tabungan != null){

        double total =
                tabungan.getJumlahTerkumpul() + nominal;

        tabungan.setJumlahTerkumpul(total);

        tabunganRepository.save(tabungan);
    }

    return "redirect:/tabungan";
}
}