package com.example.demo.repository;

import com.example.demo.model.Transaksi;
import com.example.demo.model.User;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransaksiRepository
        extends JpaRepository<Transaksi, Long> {

    List<Transaksi> findByUser(User user);
}