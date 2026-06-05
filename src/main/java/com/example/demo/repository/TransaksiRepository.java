package com.example.demo.repository;

import com.example.demo.model.Transaksi;
import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TransaksiRepository
        extends JpaRepository<Transaksi, Long> {

    List<Transaksi> findByUser(User user);

    List<Transaksi> findByUser_Id(Long userId);

@Query("""
SELECT COALESCE(SUM(t.jumlah),0)
FROM Transaksi t
WHERE t.user.id = :userId
AND t.jenis = 'Pemasukan'
""")
Double totalPemasukan(Long userId);

@Query("""
SELECT COALESCE(SUM(t.jumlah),0)
FROM Transaksi t
WHERE t.user.id = :userId
AND t.jenis = 'Pengeluaran'
""")
Double totalPengeluaran(Long userId);
}