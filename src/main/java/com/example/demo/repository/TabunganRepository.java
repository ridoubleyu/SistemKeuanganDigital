package com.example.demo.repository;

import com.example.demo.model.Tabungan;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TabunganRepository
        extends JpaRepository<Tabungan, Long> {
        List<Tabungan> findByUserId(Long userId);
}