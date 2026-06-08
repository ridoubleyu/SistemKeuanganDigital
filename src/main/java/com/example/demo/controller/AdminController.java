package com.example.demo.controller;

import com.example.demo.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.repository.TransaksiRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.ui.Model;
import org.springframework.security.core.Authentication;
import java.text.NumberFormat;
import java.util.Locale;



@Controller
public class AdminController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TransaksiRepository transaksiRepository;

@GetMapping("/admin")
public String adminPage(
        Model model,
        Authentication authentication
){

    String email = authentication.getName();

    User user = userRepository.findByEmail(email)
            .orElse(null);

    if(user == null){
        return "redirect:/login";
    }

    if(!"ADMIN".equals(user.getRole())){
        return "redirect:/dashboard";
    }

    Double totalSaldo =
            transaksiRepository.getTotalSaldo();

    Double totalPemasukan =
            transaksiRepository.getTotalPemasukan();

    NumberFormat rupiah =
            NumberFormat.getCurrencyInstance(
                    new Locale("id", "ID")
            );

    model.addAttribute(
            "users",
            userRepository.findAll()
    );

    model.addAttribute(
            "totalUser",
            userRepository.count()
    );

    model.addAttribute(
            "totalTransaksi",
            transaksiRepository.count()
    );

    model.addAttribute(
            "totalSaldo",
            totalSaldo != null
                    ? rupiah.format(totalSaldo)
                    : "Rp 0"
    );

    model.addAttribute(
            "totalPemasukan",
            totalPemasukan != null
                    ? rupiah.format(totalPemasukan)
                    : "Rp 0"
    );

    return "admin/dashboard-admin";
}

@GetMapping("/admin/users")
public String kelolaUser(
        Model model,
        Authentication authentication
){

    String email = authentication.getName();

    User user = userRepository.findByEmail(email)
            .orElse(null);

    if(user == null){
        return "redirect:/login";
    }

    if(!"ADMIN".equals(user.getRole())){
        return "redirect:/dashboard";
    }

    model.addAttribute(
            "users",
            userRepository.findAll()
    );

    model.addAttribute(
            "newUser",
            new User()
    );

    return "admin/users";
}
@GetMapping("/admin/laporan")
public String laporanPage(
        Model model,
        Authentication authentication
){

    String email = authentication.getName();

    User user = userRepository.findByEmail(email)
            .orElse(null);

    if(user == null){
        return "redirect:/login";
    }

    if(!"ADMIN".equals(user.getRole())){
        return "redirect:/dashboard";
    }

    Double pemasukan =
            transaksiRepository.getTotalPemasukan();

    Double pengeluaran =
            transaksiRepository.getTotalPengeluaran();

    model.addAttribute(
            "pemasukan",
            pemasukan != null ? pemasukan : 0
    );

    model.addAttribute(
            "pengeluaran",
            pengeluaran != null ? pengeluaran : 0
    );

    model.addAttribute(
            "totalUser",
            userRepository.count()
    );

    model.addAttribute(
            "totalTransaksi",
            transaksiRepository.count()
    );

    return "admin/laporan";
}
@GetMapping("/admin/settings")
public String settingsPage(
        Model model,
        Authentication authentication
){

    String email = authentication.getName();

    User user = userRepository.findByEmail(email)
            .orElse(null);

    if(user == null){
        return "redirect:/login";
    }

    if(!"ADMIN".equals(user.getRole())){
        return "redirect:/dashboard";
    }

    model.addAttribute(
            "admin",
            user
    );

    return "admin/settings";
}
@GetMapping("/admin/transaksi")
public String transaksiPage(
        Model model,
        Authentication authentication
){

    String email = authentication.getName();

    User user = userRepository.findByEmail(email)
            .orElse(null);

    if(user == null){
        return "redirect:/login";
    }

    if(!"ADMIN".equals(user.getRole())){
        return "redirect:/dashboard";
    }

    model.addAttribute(
            "transaksi",
            transaksiRepository.findAll()
    );

    return "admin/transaksi";
}
}