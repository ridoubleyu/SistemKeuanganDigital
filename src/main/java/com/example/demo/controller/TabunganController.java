package com.example.demo.controller;

import com.example.demo.model.Tabungan;
import com.example.demo.repository.TabunganRepository;
import com.example.demo.repository.TransaksiRepository;
import com.example.demo.model.Transaksi;
import com.example.demo.model.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class TabunganController {

    @Autowired
    private TabunganRepository tabunganRepository;
    @Autowired
    private TransaksiRepository transaksiRepository;

@GetMapping("/tabungan")
public String tabunganPage(
        Model model,
        HttpSession session){
        User user = (User) session.getAttribute("user");

model.addAttribute(
        "listTabungan",
        tabunganRepository.findByUserId(user.getId())
);
        return "tabungan";
    }

    @PostMapping("/tabungan")
    public String tambahTabungan(
           HttpSession session,
            @RequestParam String namaTarget,
            @RequestParam Double targetJumlah
    ){

        Tabungan tabungan = new Tabungan();

        tabungan.setNamaTarget(namaTarget);
        tabungan.setTargetJumlah(targetJumlah);
        tabungan.setJumlahTerkumpul(0.0);
        User user = (User) session.getAttribute("user");
        tabungan.setUserId(user.getId());
        tabunganRepository.save(tabungan);

        return "redirect:/tabungan";
    }
@PostMapping("/tabungan/tambah/{id}")
public String tambahUangTabungan(

        @PathVariable Long id,
        @RequestParam Double nominal
){

    Tabungan tabungan =
            tabunganRepository.findById(id).orElse(null);

    if(tabungan != null){

        // TAMBAH TABUNGAN
        double total =
                tabungan.getJumlahTerkumpul() + nominal;

        tabungan.setJumlahTerkumpul(total);

        tabunganRepository.save(tabungan);

        // BUAT TRANSAKSI PENGELUARAN
        Transaksi transaksi = new Transaksi();

        transaksi.setNama(
                "Menabung - " + tabungan.getNamaTarget()
        );

        transaksi.setJumlah(nominal);

        transaksi.setJenis("Pengeluaran");

        transaksiRepository.save(transaksi);
    }

    return "redirect:/tabungan";
}
}