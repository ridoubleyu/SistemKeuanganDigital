package com.example.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "anggaran")
public class Anggaran {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long anggaranId;

    private String periode;

    private String kategori;

    private Double jumlahTarget;

    private Double jumlahTerpakai = 0.0;

    private Long userId;

    // GETTER SETTER

    public Long getAnggaranId() {
        return anggaranId;
    }

    public void setAnggaranId(Long anggaranId) {
        this.anggaranId = anggaranId;
    }

    public String getPeriode() {
        return periode;
    }

    public void setPeriode(String periode) {
        this.periode = periode;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public Double getJumlahTarget() {
        return jumlahTarget;
    }

    public void setJumlahTarget(Double jumlahTarget) {
        this.jumlahTarget = jumlahTarget;
    }

    public Double getJumlahTerpakai() {
        return jumlahTerpakai;
    }

    public void setJumlahTerpakai(Double jumlahTerpakai) {
        this.jumlahTerpakai = jumlahTerpakai;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}