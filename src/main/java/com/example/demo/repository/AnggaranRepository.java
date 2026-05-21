package com.example.demo.repository;

import com.example.demo.model.Anggaran;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AnggaranRepository
        extends JpaRepository<Anggaran, Long> {

}