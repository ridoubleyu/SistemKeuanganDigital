package com.example.demo.controller;

import com.example.demo.model.Anggaran;
import com.example.demo.model.User;
import com.example.demo.repository.AnggaranRepository;
import com.example.demo.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AnggaranController {

    @Autowired
    private AnggaranRepository anggaranRepository;

    @Autowired
    private UserRepository userRepository;

    // =========================
    // HALAMAN ANGGARAN
    // =========================
    @GetMapping("/anggaran")
    public String anggaranPage(
            Authentication authentication,
            Model model
    ){

        if(authentication == null){
            return "redirect:/login";
        }

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElse(null);

        if(user == null){
            return "redirect:/login";
        }

        model.addAttribute(
                "listAnggaran",
                anggaranRepository.findByUserId(user.getId())
        );

        return "anggaran";
    }

@PostMapping("/anggaran")
public String tambahAnggaran(
        @RequestParam String periode,
        @RequestParam String kategori,
        @RequestParam Double jumlahTarget,
        Authentication authentication
){

    if(authentication == null){
        return "redirect:/login";
    }

    String email = authentication.getName();

    User user = userRepository.findByEmail(email)
            .orElse(null);

    if(user == null){
        return "redirect:/login";
    }

    Anggaran anggaran = new Anggaran();

    anggaran.setPeriode(periode);
    anggaran.setKategori(kategori.trim());
    anggaran.setJumlahTarget(jumlahTarget);
    anggaran.setJumlahTerpakai(0.0);
    anggaran.setUserId(user.getId());

    anggaranRepository.save(anggaran);

    return "redirect:/anggaran";
}

    // =========================
    // HALAMAN EDIT
    // =========================
    @GetMapping("/anggaran/edit/{id}")
    public String editAnggaran(
            @PathVariable Long id,
            Model model
    ){

        Anggaran anggaran =
                anggaranRepository.findById(id)
                        .orElse(null);

        if(anggaran == null){
            return "redirect:/anggaran";
        }

        model.addAttribute(
                "anggaran",
                anggaran
        );

        return "edit-anggaran";
    }

    // =========================
    // UPDATE ANGGARAN
    // =========================
    @PostMapping("/anggaran/update")
    public String updateAnggaran(

            @RequestParam Long id,
            @RequestParam String periode,
            @RequestParam String kategori,
            @RequestParam Double jumlahTarget
    ){

        Anggaran anggaran =
                anggaranRepository.findById(id)
                        .orElse(null);

        if(anggaran == null){
            return "redirect:/anggaran";
        }

        anggaran.setPeriode(periode);
        anggaran.setKategori(kategori);
        anggaran.setJumlahTarget(jumlahTarget);

        anggaranRepository.save(anggaran);

        return "redirect:/anggaran";
    }

}