package com.example.demo.controller;

import com.example.demo.model.Anggaran;
import com.example.demo.model.Transaksi;
import com.example.demo.model.User;
import com.example.demo.repository.TransaksiRepository;
import com.example.demo.repository.AnggaranRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;

@Controller
public class TransaksiController {

    @Autowired
    private TransaksiRepository transaksiRepository;

    @Autowired
    private AnggaranRepository anggaranRepository;

@GetMapping("/transaksi")
public String transaksiPage(
        Model model,
        HttpSession session){

    User user = (User) session.getAttribute("user");

    if(user == null){
        return "redirect:/login";
    }

    model.addAttribute(
            "listTransaksi",
            transaksiRepository.findByUser(user)
    );

    return "transaksi";
}

@PostMapping("/transaksi")
public String tambahTransaksi(

    @RequestParam String nama,
    @RequestParam Double jumlah,
    @RequestParam String jenis,
    @RequestParam String kategori,
    HttpSession session
) {

    // AMBIL USER YANG LOGIN
    User user = (User) session.getAttribute("user");

    if(user == null){
        return "redirect:/login";
    }

    // SIMPAN TRANSAKSI
    Transaksi transaksi = new Transaksi();

    transaksi.setNama(nama);
    transaksi.setJumlah(jumlah);
    transaksi.setJenis(jenis);
    transaksi.setKategori(kategori);
    transaksi.setUser(user);

    transaksiRepository.save(transaksi);

    // UPDATE ANGGARAN
    if(jenis.equalsIgnoreCase("Pengeluaran")){

        List<Anggaran> listAnggaran =
                anggaranRepository.findAll();

        for(Anggaran a : listAnggaran){

            if(a.getKategori().equalsIgnoreCase(kategori)){

                Double totalTerpakai =
                        a.getJumlahTerpakai() + jumlah;

                a.setJumlahTerpakai(totalTerpakai);

                anggaranRepository.save(a);
            }
        }
    }

    return "redirect:/transaksi";
}
    @GetMapping("/transaksi/delete/{id}")
    public String hapusTransaksi(@PathVariable Long id){

        transaksiRepository.deleteById(id);

        return "redirect:/transaksi";
    }

    @GetMapping("/transaksi/edit/{id}")
    public String editPage(@PathVariable Long id, Model model){

        Transaksi transaksi =
                transaksiRepository.findById(id).orElse(null);

        if(transaksi == null){
            return "redirect:/transaksi";
        }

        model.addAttribute("transaksi", transaksi);

        return "edit-transaksi";
    }

    @PostMapping("/transaksi/update")
    public String updateTransaksi(

            @RequestParam Long id,
            @RequestParam String nama,
            @RequestParam Double jumlah,
            @RequestParam String jenis,
            @RequestParam String kategori
    ){

        Transaksi transaksi =
                transaksiRepository.findById(id).orElse(null);

        if(transaksi == null){
            return "redirect:/transaksi";
        }

        transaksi.setNama(nama);
        transaksi.setJumlah(jumlah);
        transaksi.setJenis(jenis);
        transaksi.setKategori(kategori);
        transaksiRepository.save(transaksi);

        return "redirect:/transaksi";
    }
}