package com.example.demo.controller;

import com.example.demo.model.Anggaran;
import com.example.demo.model.Transaksi;
import com.example.demo.model.User;
import com.example.demo.repository.TransaksiRepository;
import com.example.demo.repository.AnggaranRepository;
import com.example.demo.repository.UserRepository;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import java.time.LocalDate;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;


@Controller
public class TransaksiController {

    @Autowired
    private TransaksiRepository transaksiRepository;

    @Autowired
    private AnggaranRepository anggaranRepository;

    @Autowired
private UserRepository userRepository;

@GetMapping("/transaksi")
public String transaksiPage(
        Authentication authentication,
        Model model
){

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
    transaksi.setTanggal(LocalDate.now());
    transaksi.setUser(user);

    transaksiRepository.save(transaksi);

    // UPDATE ANGGARAN
    if(jenis.equalsIgnoreCase("Pengeluaran")){

    List<Anggaran> listAnggaran =
            anggaranRepository.findByUserId(user.getId());

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

    Transaksi transaksi =
            transaksiRepository.findById(id).orElse(null);

    if(transaksi != null){

        if(transaksi.getJenis().equalsIgnoreCase("Pengeluaran")){

            List<Anggaran> listAnggaran =
                    anggaranRepository.findByUserId(
                            transaksi.getUser().getId()
                    );

            for(Anggaran a : listAnggaran){

                if(a.getKategori().equalsIgnoreCase(
                        transaksi.getKategori()
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

    // DATA LAMA
    String kategoriLama =
            transaksi.getKategori();

    Double jumlahLama =
            transaksi.getJumlah();

    String jenisLama =
            transaksi.getJenis();

    // VALIDASI
    if(jenis.equals("Pemasukan")){

        if(!kategori.equals("Gaji")
                && !kategori.equals("Bonus")
                && !kategori.equals("Freelance")
                && !kategori.equals("Investasi")){

            return "redirect:/transaksi?error=kategori";
        }
    }

    if(jenis.equals("Pengeluaran")){

        if(!kategori.equals("Makan")
                && !kategori.equals("Transport")
                && !kategori.equals("Belanja")
                && !kategori.equals("Hiburan")){

            return "redirect:/transaksi?error=kategori";
        }
    }

    // UPDATE DATA TRANSAKSI
    transaksi.setNama(nama);
    transaksi.setJumlah(jumlah);
    transaksi.setJenis(jenis);
    transaksi.setKategori(kategori);

    transaksiRepository.save(transaksi);

    // UPDATE ANGGARAN
    if(jenisLama.equals("Pengeluaran")){

        List<Anggaran> listAnggaran =
                anggaranRepository.findByUserId(
                        transaksi.getUser().getId()
                );

        for(Anggaran a : listAnggaran){

            // Kurangi anggaran lama
            if(a.getKategori().equalsIgnoreCase(kategoriLama)){

                a.setJumlahTerpakai(
                        a.getJumlahTerpakai() - jumlahLama
                );

                anggaranRepository.save(a);
            }

            // Tambah anggaran baru
            if(jenis.equals("Pengeluaran")
                    && a.getKategori().equalsIgnoreCase(kategori)){

                a.setJumlahTerpakai(
                        a.getJumlahTerpakai() + jumlah
                );

                anggaranRepository.save(a);
            }
        }
    }

    return "redirect:/transaksi";
}
}