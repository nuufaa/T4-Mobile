# T4 Mobile - Student Contact App

## 👤 Identitas Mahasiswa
- Fitri Nufa Dastana
- F1D02310052

## 📱 Deskripsi Aplikasi
Student Contact App merupakan aplikasi Android berbasis Kotlin yang digunakan untuk mengelola data mahasiswa. Aplikasi ini menerapkan fitur CRUD (Create, Read, Update, Delete) menggunakan Room Database sebagai penyimpanan data utama.

Aplikasi memiliki fitur:
- Menampilkan daftar mahasiswa
- Menambahkan data mahasiswa
- Mengedit data mahasiswa
- Menghapus data mahasiswa
- Mencari mahasiswa berdasarkan nama atau NIM
- Menyimpan data lokal menggunakan Room Database
- Menampilkan daftar catatan dari Internal Storage

## 🖼️ Screenshot Aplikasi

### Halaman Login
<img width="357" height="798" alt="image" src="https://github.com/user-attachments/assets/6669bbb0-c1aa-48f6-a1a2-f3ca5cda3d01" />

### Halaman Daftar Mahasiswa
<img width="359" height="800" alt="image" src="https://github.com/user-attachments/assets/a30a0990-8e9d-4c3d-a679-0b792ad1aee1" />

### Halaman Detail Mahasiswa
<img width="358" height="795" alt="image" src="https://github.com/user-attachments/assets/bf7d4786-6cc9-4153-adf8-b901eba7a2b6" />

### Halaman Form Tambah Mahasiswa
<img width="356" height="793" alt="image" src="https://github.com/user-attachments/assets/ea3ab399-5a56-412d-9fde-d61ac2a76c33" />

### Halaman Search
- Jika keyword kosong, tampilkan semua data
  <img width="359" height="798" alt="image" src="https://github.com/user-attachments/assets/ae062343-0296-43cf-89ed-8cd0bc938da4" />
  
- Saat user mengetik keyword, panggil studentDao.searchStudents(keyword)
  <img width="357" height="799" alt="image" src="https://github.com/user-attachments/assets/ea7350ce-e19f-44fc-9c4f-b7c20777f4d0" />

### Halaman Profile
<img width="354" height="794" alt="image" src="https://github.com/user-attachments/assets/2ed752e6-28be-41d3-848e-d499f648b27f" />

### Halaman Daftar Catatan
<img width="356" height="796" alt="image" src="https://github.com/user-attachments/assets/a5c95fa7-0c26-4e06-bae1-a228ad76f290" />

## 💾 Metode Penyimpanan yang Digunakan

### 1. SharedPreferences
Digunakan untuk menyimpan data sederhana seperti status login pengguna.

**Alasan penggunaan:**
- Mudah digunakan
- Cocok untuk data kecil
- Penyimpanan ringan dan cepat

### 2. Internal Storage
Digunakan untuk menyimpan file catatan (notes) di dalam memori internal aplikasi.

**Alasan penggunaan:**
- Data lebih aman karena hanya dapat diakses aplikasi sendiri
- Cocok untuk penyimpanan file teks sederhana


### 3. Room Database
Digunakan untuk menyimpan data mahasiswa secara terstruktur.

**Alasan penggunaan:**
- Mendukung operasi CRUD dengan mudah
- Query database lebih aman
- Terintegrasi dengan Kotlin Coroutines
- Lebih modern dibanding SQLite biasa

## ⚠️ Kendala yang Dihadapi

### 1. Konflik Plugin Kotlin dan KAPT
Saat menambahkan Room Database, terjadi error plugin Kotlin karena plugin dimuat dua kali.

**Cara mengatasi:**
- Menghapus plugin yang duplikat
- Menggunakan Version Catalog (`libs.versions.toml`) secara konsisten

### 2. RecyclerView Tidak Menampilkan Data
RecyclerView awalnya tidak menampilkan data dari database.

**Cara mengatasi:**
- Menggunakan `lifecycleScope.launch`
- Memastikan DAO dan Adapter sudah terhubung dengan benar

### 3. Error Saat Build Gradle
Terjadi error dependency dan sinkronisasi Gradle.

**Cara mengatasi:**
- Memperbaiki konfigurasi dependency Room
- Melakukan Sync Gradle ulang
- Menggunakan dependency versi yang sesuai
