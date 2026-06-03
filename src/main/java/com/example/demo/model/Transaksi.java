package com.example.demo.model;

import java.time.LocalDate;

import jakarta.persistence.*;

@Entity
@Table(name = "transaksi")
public class Transaksi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nama;

    private Double jumlah;

    private String jenis;

    private String kategori;

    private LocalDate tanggal;

    @ManyToOne
@JoinColumn(name = "user_id")
private User user;

public User getUser() {
    return user;
}

public void setUser(User user) {
    this.user = user;
}

    // GETTER SETTER

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getTanggal() {
    return tanggal;
}

public void setTanggal(LocalDate tanggal) {
    this.tanggal = tanggal;
}

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public Double getJumlah() {
        return jumlah;
    }

    public void setJumlah(Double jumlah) {
        this.jumlah = jumlah;
    }

    public String getJenis() {
        return jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

    public String getKategori() {
    return kategori;
    }

    public void setKategori(String kategori) {
    this.kategori = kategori;
    }

}