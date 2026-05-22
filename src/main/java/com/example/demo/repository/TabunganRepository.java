package com.example.demo.repository;

import com.example.demo.model.Tabungan;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TabunganRepository
        extends JpaRepository<Tabungan, Long> {

}