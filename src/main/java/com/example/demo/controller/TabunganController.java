package com.example.demo.controller;

import com.example.demo.model.Tabungan;
import com.example.demo.model.Transaksi;
import com.example.demo.model.User;

import com.example.demo.repository.TabunganRepository;
import com.example.demo.repository.TransaksiRepository;
import com.example.demo.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
public class TabunganController {

    @Autowired
    private TabunganRepository tabunganRepository;

    @Autowired
    private TransaksiRepository transaksiRepository;

    @Autowired
    private UserRepository userRepository;

    // =========================
    // HALAMAN TABUNGAN
    // =========================
    @GetMapping("/tabungan")
    public String tabunganPage(
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
                "listTabungan",
                tabunganRepository.findByUserId(user.getId())
        );

        return "tabungan";
    }

    // =========================
    // TAMBAH TARGET TABUNGAN
    // =========================
    @PostMapping("/tabungan")
    public String tambahTabungan(

            @RequestParam String namaTarget,
            @RequestParam Double targetJumlah,
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

        Tabungan tabungan = new Tabungan();

        tabungan.setNamaTarget(namaTarget);
        tabungan.setTargetJumlah(targetJumlah);
        tabungan.setJumlahTerkumpul(0.0);

        tabungan.setUserId(user.getId());

        tabunganRepository.save(tabungan);

        return "redirect:/tabungan";
    }

    // =========================
    // TAMBAH UANG TABUNGAN
    // =========================
    @PostMapping("/tabungan/tambah/{id}")
    public String tambahUangTabungan(

            @PathVariable Long id,
            @RequestParam Double nominal,
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

        Double pemasukan =
                transaksiRepository.totalPemasukan(user.getId());

        Double pengeluaran =
                transaksiRepository.totalPengeluaran(user.getId());

        if(pemasukan == null) pemasukan = 0.0;
        if(pengeluaran == null) pengeluaran = 0.0;

        Double saldo = pemasukan - pengeluaran;

        Tabungan tabungan =
                tabunganRepository.findById(id).orElse(null);

        if(tabungan == null){
            return "redirect:/tabungan";
        }

        if(!tabungan.getUserId().equals(user.getId())){
            return "redirect:/tabungan";
        }

        if(nominal <= 0){
            return "redirect:/tabungan";
        }

        if(nominal > saldo){
            return "redirect:/tabungan?error=saldo";
        }

        // TAMBAH TABUNGAN
        double total =
                tabungan.getJumlahTerkumpul() + nominal;

        tabungan.setJumlahTerkumpul(total);

        tabunganRepository.save(tabungan);

        // SIMPAN KE TRANSAKSI
        Transaksi transaksi = new Transaksi();

        transaksi.setNama(
                tabungan.getNamaTarget()
        );

        transaksi.setJumlah(nominal);

        transaksi.setJenis("Pengeluaran");

        transaksi.setKategori("Menabung");

        transaksi.setUser(user);

        transaksi.setTanggal(LocalDate.now());

        transaksiRepository.save(transaksi);

        return "redirect:/tabungan";
    }

}