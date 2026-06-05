# Implementasi Komputasi Paralel

## Latar Belakang

Pada Sistem Keuangan Digital, dashboard menampilkan ringkasan data keuangan pengguna secara real-time. Informasi yang ditampilkan meliputi total pemasukan, total pengeluaran, dan jumlah transaksi yang telah dilakukan.

Apabila ketiga proses perhitungan tersebut dijalankan secara berurutan (sequential), sistem harus menunggu satu proses selesai sebelum menjalankan proses berikutnya. Hal ini dapat meningkatkan waktu respons terutama ketika jumlah data transaksi semakin besar.

Untuk meningkatkan efisiensi, sistem dapat menerapkan konsep komputasi paralel dengan memanfaatkan multithreading sehingga beberapa proses perhitungan dapat dijalankan secara bersamaan.

---

## Konsep Paralelisme

Pada implementasi dashboard, terdapat tiga tugas utama yang dapat diproses secara independen:

### Thread 1 – Perhitungan Total Pemasukan

Thread pertama bertugas mengambil seluruh data transaksi pemasukan dari database kemudian menghitung total nilai pemasukan yang dimiliki pengguna.

Output:

* Total pemasukan pengguna.

### Thread 2 – Perhitungan Total Pengeluaran

Thread kedua bertugas mengambil seluruh data transaksi pengeluaran dan menghitung total pengeluaran pengguna.

Output:

* Total pengeluaran pengguna.

### Thread 3 – Perhitungan Jumlah Transaksi

Thread ketiga bertugas menghitung jumlah seluruh transaksi yang dimiliki pengguna tanpa memperhatikan kategori transaksi.

Output:

* Jumlah transaksi pengguna.

---

## Arsitektur Proses Paralel

```text
Dashboard Request
        │
        ▼
 ┌─────────────┐
 │ Main Thread │
 └──────┬──────┘
        │
        ├──────────────► Thread 1
        │                Hitung Total Pemasukan
        │
        ├──────────────► Thread 2
        │                Hitung Total Pengeluaran
        │
        └──────────────► Thread 3
                         Hitung Jumlah Transaksi

        ▼
Menunggu seluruh thread selesai

        ▼
Gabungkan hasil

        ▼
Tampilkan Dashboard
```

---

## Implementasi Menggunakan Java

Implementasi paralel dapat dilakukan menggunakan ExecutorService atau CompletableFuture pada Java.

Contoh pendekatan:

```java
CompletableFuture<Double> totalPemasukan =
        CompletableFuture.supplyAsync(() ->
                transaksiService.hitungTotalPemasukan(userId));

CompletableFuture<Double> totalPengeluaran =
        CompletableFuture.supplyAsync(() ->
                transaksiService.hitungTotalPengeluaran(userId));

CompletableFuture<Long> jumlahTransaksi =
        CompletableFuture.supplyAsync(() ->
                transaksiService.hitungJumlahTransaksi(userId));

CompletableFuture.allOf(
        totalPemasukan,
        totalPengeluaran,
        jumlahTransaksi
).join();
```

Dengan pendekatan tersebut, ketiga proses dapat berjalan secara bersamaan pada thread yang berbeda.

---

## Analisis Keuntungan

Penerapan komputasi paralel pada dashboard memberikan beberapa keuntungan:

1. Mengurangi waktu respons dashboard.
2. Memanfaatkan sumber daya prosesor secara lebih optimal.
3. Meningkatkan performa ketika jumlah transaksi bertambah.
4. Memberikan pengalaman pengguna yang lebih baik karena data dapat ditampilkan lebih cepat.

---

## Kesimpulan

Komputasi paralel dapat diterapkan pada Sistem Keuangan Digital dengan membagi proses perhitungan dashboard ke dalam beberapa thread yang berjalan secara bersamaan. Pada implementasi ini, total pemasukan, total pengeluaran, dan jumlah transaksi dihitung oleh thread yang berbeda sehingga waktu pemrosesan menjadi lebih efisien dibandingkan pendekatan sequential.
