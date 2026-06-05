# Entity Relationship Diagram (ERD)

[⬅ Kembali ke Beranda](index.md)

## Gambaran Umum Database

Database Sistem Keuangan Digital dirancang untuk menyimpan seluruh data pengguna dan aktivitas keuangan yang dilakukan di dalam sistem. Struktur database dibuat agar mampu mendukung proses pengelolaan anggaran dan tabungan secara efisien.

## Entitas Utama

### User

Entitas User digunakan untuk menyimpan data pengguna yang terdaftar dalam sistem.

Atribut:

* user_id
* nama
* email
* password

### Anggaran

Entitas Anggaran digunakan untuk menyimpan informasi anggaran yang dibuat oleh pengguna.

Atribut:

* anggaran_id
* nama_anggaran
* jumlah_anggaran
* tanggal_mulai
* tanggal_selesai
* user_id

### Tabungan

Entitas Tabungan digunakan untuk menyimpan data tabungan yang dimiliki pengguna.

Atribut:

* tabungan_id
* nama_tabungan
* target_tabungan
* saldo_tabungan
* user_id

## Relasi Antar Entitas

* Satu User dapat memiliki banyak Anggaran (One-to-Many).
* Satu User dapat memiliki banyak Tabungan (One-to-Many).

## Diagram ERD
<img width="1600" height="1438" alt="image" src="https://github.com/user-attachments/assets/a2190cf7-84ef-4a34-ac75-d79243bc24ff" />
