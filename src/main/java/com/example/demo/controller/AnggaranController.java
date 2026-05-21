package com.example.demo.controller;

import com.example.demo.model.Anggaran;
import com.example.demo.repository.AnggaranRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AnggaranController {

    @Autowired
    private AnggaranRepository anggaranRepository;

    @GetMapping("/anggaran")
    public String anggaranPage(Model model){

        model.addAttribute(
                "listAnggaran",
                anggaranRepository.findAll()
        );

        return "anggaran";
    }

    @PostMapping("/anggaran")
    public String tambahAnggaran(

            @RequestParam String periode,
            @RequestParam String kategori,
            @RequestParam Double jumlahTarget
    ){

        Anggaran anggaran = new Anggaran();

        anggaran.setPeriode(periode);
        anggaran.setKategori(kategori);
        anggaran.setJumlahTarget(jumlahTarget);

        anggaran.setJumlahTerpakai(0.0);

        anggaran.setUserId(1L);

        anggaranRepository.save(anggaran);

        return "redirect:/anggaran";
    }

    @GetMapping("/anggaran/delete/{id}")
    public String hapusAnggaran(@PathVariable Long id){

        anggaranRepository.deleteById(id);

        return "redirect:/anggaran";
    }
}