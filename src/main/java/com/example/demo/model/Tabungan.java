package com.example.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "tabungan")
public class Tabungan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tabunganId;

    private String namaTarget;

    private Double targetJumlah;

    private Double jumlahTerkumpul = 0.0;

    private Long userId;

    // GETTER SETTER

    public Long getTabunganId() {
        return tabunganId;
    }

    public void setTabunganId(Long tabunganId) {
        this.tabunganId = tabunganId;
    }

    public String getNamaTarget() {
        return namaTarget;
    }

    public void setNamaTarget(String namaTarget) {
        this.namaTarget = namaTarget;
    }

    public Double getTargetJumlah() {
        return targetJumlah;
    }

    public void setTargetJumlah(Double targetJumlah) {
        this.targetJumlah = targetJumlah;
    }

    public Double getJumlahTerkumpul() {
        return jumlahTerkumpul;
    }

    public void setJumlahTerkumpul(Double jumlahTerkumpul) {
        this.jumlahTerkumpul = jumlahTerkumpul;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}