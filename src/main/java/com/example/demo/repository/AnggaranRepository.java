package com.example.demo.repository;

import com.example.demo.model.Anggaran;
import com.example.demo.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AnggaranRepository
        extends JpaRepository<Anggaran, Long> {

    List<Anggaran> findByUserId(Long userId);
}