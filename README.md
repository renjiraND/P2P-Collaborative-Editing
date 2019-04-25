# Peer-to-Peer Collaborative Editing

Kelompok 03 Kelas 02
- 13516014 - Renjira Naufhal Dhiaegana
- 13516017 - Muhammad Nurraihan Naufal
- 13516134 - Shevalda Gracielira

*Collaborative Editing* ini menggunakan ***Conflict-free Replicated Data Type (CRDT)***

Ada beberapa asumsi ketika menjalankan program:
* Pengguna hanya dapat menggunakan arrow key, space, key alfabet, dan key numerik.
* Pengguna perlu memasukan port untuk menghubungkan site/node

## Petunjuk Penggunaan Program

1. Clone respitory ini
2. Pada root directory, jalankan `cd out\production\peer2peer-collaborativeediting`
2. Kemudian jalankan `java com.collaborativeediting.app.Main` 

## Pembagian Tugas
| NIM      | Persentase Pengerjaan | Deskripsi Singkat                 |
| -------- | --------------------- | --------------------------------- |
| 13516014 | 33.33%                | Version, VersionVector, Messenger |
| 13516017 | 33.33%                | CRDT, Laporan                     |
| 13516134 | 33.33%                | Controller, GUIFrame              |

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

CRDT berfungsi untuk mengurusi `characters`, sebuah ArrayList dari kelas `Character` yang menyimpan data karakter yang akan muncul di text editor. CRDT juga bertugas untuk memastikan men-*encode* `Character` supaya `Messenger` dapat mengirimkan String ke site lain dan men-*decode* `Character` supaya Controller dapat memberikan perintah kepada CRDT berupa `Character`.

#### 2. Version Vector

Version Vector berfungsi untuk mengatur versi operasi apa saja yang sudah diterima dari site lain. Version Vector membantu Controller kapan melakukan perubahan pada Deletion Buffer

#### 3. Deletion Buffer

Deletion Buffer berfungsi untuk menyimpan Character yang akan di-*delete* namun belum ada perintah *insert* untuk Character tersebut. Jika Deletion Buffer kosong, maka tidak ada karakter yang akan dihapus. Jika ada element di dalam Deletion Buffer, maka Controller harus mengecek apakah Character yang akan di-*delete* sudah ditambah ke `characters` yang ada di Controller.


### Desain Struktur Data

Berikut struktur data yang kami buat:

1. CRDT

```
List<Character> characters;
int id;
int counter = 0;
```

2. CRDT.Character

```
int siteId;
int siteCounter;
char value;
double position;
```

3. CRDT.Message

```
Character character;
int type;
```

4. Version Vector

```
ArrayList<Version> versions;
Version localVersion;
```

5. Version

```
long siteId;
int counter;
ArrayList<Integer> exceptions;
```

6. Messenger

```
ArrayList<Thread> ClientList;
Thread myServer;
Thread listener;
String buffer;
ArrayList<Integer> ClientListInteger;
```

### Analisis

Menurut kami, program kami sudah cukup baik mempertimbangkan waktu yang diberikan. Namun, tentu saja ada penambahan yang dapat dilakukan sehingga program kami dapat menjadi lebih baik.

Pertama, ada beberapa kelas yang terlalu tergantung dengan satu sama lain sehingga tidak dapat berdiri sendiri. Seharusnya, dengan perbaikan kelas, struktur data, dan fungsi yang lebih sedikit dan fokus, program kami dapat memiliki kelas yang tidak terlalu tergantung dengan kelas yang lain yang tidak memiliki hubungan langsung.

Kedua, program kami masih belum mengimplementasikan algoritma secara efektif sehingga mungkin terjadi *latency* yang tinggi karena waktu eksekusi yang tinggi. Dengan perbaikan algoritma yang lebih efektif, program kami dapat befungsi lebih baik.

### Kasus Uji

### Screenshot / Video Program

![](https://gitlab.informatika.org/shevalda/peer2peer-collaborative-editing/blob/master/screenshot/messageImage_1556156715818.jpg)

![](https://gitlab.informatika.org/shevalda/peer2peer-collaborative-editing/blob/master/screenshot/messageImage_1556156744304.jpg)

![](https://gitlab.informatika.org/shevalda/peer2peer-collaborative-editing/blob/master/screenshot/messageImage_1556156781938.jpg)
