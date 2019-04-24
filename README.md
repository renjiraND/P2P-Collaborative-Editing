# Peer-to-Peer Collaborative Editing

*Collaborative Editing* ini menggunakan ***Conflict-free Replicated Data Type (CRDT)***

Kelompok 03 Kelas 02
- 13516014 - Renjira Naufhal Dhiaegana
- 13516017 - Muhammad Nurraihan Naufal
- 13516134 - Shevalda Gracielira 

## Petunjuk Penggunaan Program


## Pembagian Tugas
| NIM      | Persentase Pengerjaan | Deskripsi Singkat                 |
| -------- | --------------------- | --------------------------------- |
| 13516014 |                       | Version, VersionVector, Messenger |
| 13516017 |                       | CRDT, Laporan                     |
| 13516134 |                       | Controller, GUIFrame              |

## Laporan

### Cara Kerja Program

Program kami terdiri dari beberapa bagian atau kelas di bawah ini berikut penjelasannya.

1. Socket
   
    Bagian ini merupakan implementasi socket programming yang akan digunakan untuk menjalankan fungsi Messenger secara umum. Terdapat tiga kelas, yaitu:

   1. Client

        Kelas ini adalah implementasi Client dalam socket programming yang memiliki fungsi untuk melakukan koneksi ke suatu port

   2. Server

        Kelas ini adalah implementasi Server dalam socket programming yang memiliki fungsi untuk

   3. Messenger
   
        Kelas ini memiliki fungsi untuk melakukan broadcast objek operasi dan menerima broadcast objek operasi. Dalam tugas ini objek operasi yang kami gunakan adalah string berisi hasil enkripsi dari objek Message.

2. CRDT
   
    Kelas ini memiliki fungsi untuk melakukan operasi terhadap CRDT seperti operasi insert dan delete. Dalam CRDT ini juga terdapat dua nested class yaitu Character sebagai implementasi sebuah karakter yang digunakan untuk algoritma CRDT ini dan Message sebagai kelas untuk pertukaran pesan dengan node lain.

3. Version

    Kelas ini adalah implementasi sebuah versi untuk digunakan pada fungsi Version Vector.

4. VersionVector

    Kelas ini memiliki fungsi untuk menyimpan version vector yang dimiliki oleh sebuah node.

5. GUIFrame

    Kelas ini memiliki fungsi untuk menampilkan antarmuka program kepada pengguna termasuk di dalamnya menerima input dan menampilkan output. Tools yang kami gunakan dalam membuat GUI ini adalah Java Swing.

6. Controller

    Kelas ini memiliki fungsi utama program yang kami buat karena seluruh proses akan dijalankan di kelas ini. Selain itu, kelas ini juga akan mengintegrasikan kelas-kelas lain yang telah disebutkan sebelumnya. Secara singkat, proses yang terjadi pada kelas ini adalah sebagai berikut.
    1. 

7.  Main

    Kelas ini memiliki fungsi utama untuk menjalankan program Controller dan melakukan socket programming supaya terhubung dengan node lain.

### Penjelasan Fungsi

#### 1. CRDT

#### 2. Version Vector

#### 3. Deletion Buffer


### Desain Struktur Data

### Analisis

*Berikan analisis terhadap program yang kalian buat, apakah ada solusi yang lebih baik (baik dari segi arsitektur maupun yang lainnya)?*

### Kasus Uji

*Beberapa kasus uji yang kalian gunakan untuk menguji kebenaran program kalian (minimal empat kasus uji)*

### Screenshot / Video Program

*Screenshot / video demo program kalian yang bekerja*
