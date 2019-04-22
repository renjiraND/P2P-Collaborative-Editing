package com.collaborativeediting.app;

public class Controller {
//  Melakukan pembaharuan CRDT

//  Melakukan pembaharuan Version Vector

//  Menerima notify operasi-operasi yang diterapkan ke text editor

// Mengubah operasi-operasi ke dalam objek operasi yang siap dikirim oleh messenger

//  Meminta messenger untuk mengirim operasi-operasi tersebut

//  Menerima notify operasi dari messenger

//  Melakukan verifikasi operasi yang diterima terhadap CRDT dan Version Vector miliknya

//  Menerapkan perubahan ke text editor

//  Menyimpan Deletion Buffer yang digunakan untuk mempertahankan causality jika terjadi kasus di mana operasi delete seharusnya dilakukan belakangan belakangan dibanding insert terhadap suatu karakter di posisi tertentu, namun karena latency operasi delete masuk terlebih dahulu.

//  Menerapkan operasi delete pada Deletion Buffer jika sudah memenuhi syarat

}
