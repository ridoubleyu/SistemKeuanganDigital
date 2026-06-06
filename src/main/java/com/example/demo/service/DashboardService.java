package com.example.demo.service;

import com.example.demo.model.Tabungan;
import com.example.demo.repository.TabunganRepository;
import com.example.demo.repository.TransaksiRepository;
import java.util.concurrent.CompletableFuture;
import com.example.demo.model.DashboardData;
import com.example.demo.model.User;
import org.springframework.stereotype.Service;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;

@Service
public class DashboardService {

    @Autowired
    private TransaksiRepository transaksiRepository;

    public DashboardData getDashboardData(Long userId) {

        CompletableFuture<Double> pemasukan =
                CompletableFuture.supplyAsync(() ->
                        transaksiRepository.totalPemasukan(userId));

        CompletableFuture<Double> pengeluaran =
                CompletableFuture.supplyAsync(() ->
                        transaksiRepository.totalPengeluaran(userId));

        CompletableFuture<Long> jumlahTransaksi =
                CompletableFuture.supplyAsync(() ->
                            transaksiRepository.countByUser_Id(userId));
        CompletableFuture.allOf(
                pemasukan,
                pengeluaran,
                jumlahTransaksi
        ).join();

        DashboardData data = new DashboardData();

        Double saldo =
        pemasukan.join() - pengeluaran.join();

        data.setTotalSaldo(saldo);

        data.setTotalPemasukan(pemasukan.join());
        data.setTotalPengeluaran(pengeluaran.join());
        data.setJumlahTransaksi(jumlahTransaksi.join());

        return data;
    }
}
