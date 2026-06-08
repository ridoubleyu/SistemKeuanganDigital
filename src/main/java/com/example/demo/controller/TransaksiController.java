package com.example.demo.controller;

import com.example.demo.model.Anggaran;
import com.example.demo.model.Transaksi;
import com.example.demo.model.User;

import com.example.demo.repository.AnggaranRepository;
import com.example.demo.repository.TransaksiRepository;
import com.example.demo.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
public class TransaksiController {

    @Autowired
    private TransaksiRepository transaksiRepository;

    @Autowired
    private AnggaranRepository anggaranRepository;

    @Autowired
    private UserRepository userRepository;

    // =========================
    // HALAMAN TRANSAKSI
    // =========================
    @GetMapping("/transaksi")
    public String transaksiPage(
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
                "listTransaksi",
                transaksiRepository.findByUser_Id(user.getId())
        );

        return "transaksi";
    }

    // =========================
    // TAMBAH TRANSAKSI
    // =========================
    @PostMapping("/transaksi")
    public String tambahTransaksi(

            @RequestParam String nama,
            @RequestParam Double jumlah,
            @RequestParam String jenis,
            @RequestParam String kategori,
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

        // =========================
        // SIMPAN TRANSAKSI
        // =========================
        Transaksi transaksi = new Transaksi();

        transaksi.setNama(nama);
        transaksi.setJumlah(jumlah);
        transaksi.setJenis(jenis);
        transaksi.setKategori(kategori);
        transaksi.setTanggal(LocalDate.now());
        transaksi.setUser(user);

        transaksiRepository.save(transaksi);

        // =========================
        // UPDATE ANGGARAN
        // =========================
        if(jenis.equalsIgnoreCase("Pengeluaran")){

            List<Anggaran> listAnggaran =
                    anggaranRepository.findByUserId(user.getId());

            for(Anggaran a : listAnggaran){

                System.out.println("Kategori transaksi: " + kategori);
                System.out.println("Kategori anggaran: " + a.getKategori());

                if(a.getKategori().trim()
                        .equalsIgnoreCase(kategori.trim())){

                    Double totalTerpakai =
                            a.getJumlahTerpakai() + jumlah;

                    a.setJumlahTerpakai(totalTerpakai);

                    anggaranRepository.save(a);

                    System.out.println("ANGGARAN BERHASIL DIUPDATE");
                }
            }
        }

        return "redirect:/transaksi";
    }

    // =========================
    // HAPUS TRANSAKSI
    // =========================
    @GetMapping("/transaksi/delete/{id}")
    public String hapusTransaksi(@PathVariable Long id){

        Transaksi transaksi =
                transaksiRepository.findById(id).orElse(null);

        if(transaksi != null){

            if(transaksi.getJenis().equalsIgnoreCase("Pengeluaran")){

                List<Anggaran> listAnggaran =
                        anggaranRepository.findByUserId(
                                transaksi.getUser().getId()
                        );

                for(Anggaran a : listAnggaran){

                    if(a.getKategori().trim()
                            .equalsIgnoreCase(
                                    transaksi.getKategori().trim()
                            )){

                        a.setJumlahTerpakai(
                                a.getJumlahTerpakai()
                                        - transaksi.getJumlah()
                        );

                        anggaranRepository.save(a);
                    }
                }
            }

            transaksiRepository.delete(transaksi);
        }

        return "redirect:/transaksi";
    }

    // =========================
    // HALAMAN EDIT
    // =========================
    @GetMapping("/transaksi/edit/{id}")
    public String editPage(
            @PathVariable Long id,
            Model model
    ){

        Transaksi transaksi =
                transaksiRepository.findById(id).orElse(null);

        if(transaksi == null){
            return "redirect:/transaksi";
        }

        model.addAttribute("transaksi", transaksi);

        return "edit-transaksi";
    }

    // =========================
    // UPDATE TRANSAKSI
    // =========================
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

        // =========================
        // DATA LAMA
        // =========================
        String kategoriLama =
                transaksi.getKategori();

        Double jumlahLama =
                transaksi.getJumlah();

        String jenisLama =
                transaksi.getJenis();

        // =========================
        // UPDATE TRANSAKSI
        // =========================
        transaksi.setNama(nama);
        transaksi.setJumlah(jumlah);
        transaksi.setJenis(jenis);
        transaksi.setKategori(kategori);

        transaksiRepository.save(transaksi);

        // =========================
        // UPDATE ANGGARAN
        // =========================
        List<Anggaran> listAnggaran =
                anggaranRepository.findByUserId(
                        transaksi.getUser().getId()
                );

        for(Anggaran a : listAnggaran){

            // KURANGI DATA LAMA
            if(jenisLama.equalsIgnoreCase("Pengeluaran")
                    &&
                    a.getKategori().trim()
                            .equalsIgnoreCase(
                                    kategoriLama.trim()
                            )){

                a.setJumlahTerpakai(
                        a.getJumlahTerpakai() - jumlahLama
                );

                anggaranRepository.save(a);
            }

            // TAMBAH DATA BARU
            if(jenis.equalsIgnoreCase("Pengeluaran")
                    &&
                    a.getKategori().trim()
                            .equalsIgnoreCase(
                                    kategori.trim()
                            )){

                a.setJumlahTerpakai(
                        a.getJumlahTerpakai() + jumlah
                );

                anggaranRepository.save(a);
            }
        }

        return "redirect:/transaksi";
    }

}