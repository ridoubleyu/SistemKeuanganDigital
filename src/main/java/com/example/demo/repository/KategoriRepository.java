package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import com.example.demo.model.Kategori;


public interface KategoriRepository
        extends JpaRepository<Kategori, Long> {

    List<Kategori> findByUserId(Long userId);
}
