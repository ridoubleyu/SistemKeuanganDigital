package com.example.demo.model;

public class DashboardData {

    private Double totalSaldo;
    private Double totalPemasukan;
    private Double totalPengeluaran;
    private Long jumlahTransaksi;

    public Double getTotalSaldo() {
        return totalSaldo;
    }

    public void setTotalSaldo(Double totalSaldo) {
        this.totalSaldo = totalSaldo;
    }

    public Double getTotalPemasukan() {
        return totalPemasukan;
    }

    public void setTotalPemasukan(Double totalPemasukan) {
        this.totalPemasukan = totalPemasukan;
    }

    public Double getTotalPengeluaran() {
        return totalPengeluaran;
    }

    public void setTotalPengeluaran(Double totalPengeluaran) {
        this.totalPengeluaran = totalPengeluaran;
    }

    public Long getJumlahTransaksi() {
        return jumlahTransaksi;
    }

    public void setJumlahTransaksi(Long jumlahTransaksi) {
        this.jumlahTransaksi = jumlahTransaksi;
    }

    public String getTotalSaldoRupiah() {

    if (totalSaldo == null) {
        return "Rp 0";
    }

    return String.format("Rp %,.0f", totalSaldo)
            .replace(",", ".");
}

public String getTotalPemasukanRupiah() {

    if (totalPemasukan == null) {
        return "Rp 0";
    }

    return String.format("Rp %,.0f", totalPemasukan)
            .replace(",", ".");
}

public String getTotalPengeluaranRupiah() {

    if (totalPengeluaran == null) {
        return "Rp 0";
    }

    return String.format("Rp %,.0f", totalPengeluaran)
            .replace(",", ".");
}
}