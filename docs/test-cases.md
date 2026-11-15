# Test case

64 test case (115 eksekusi, karena sebagian dijalankan dengan beberapa set data). Semua otomatis; nama fungsi test diawali ID-nya, misalnya `test_kh31_...`.

Teknik: **EP** equivalence partitioning · **BVA** boundary value analysis · **ST** state transition · **Neg** negative test · **E2E** alur antarperan.
Tanda: 🔥 smoke · 🔁 regresi bug yang diperbaiki · ⚠️ bug terbuka (xfail).

## KINGSHANDs (UI)

### Login dan hak akses: story [SCRUM-8](https://mochiii762025.atlassian.net/browse/SCRUM-8)

| ID | Skenario | Teknik | Data | Hasil yang diharapkan |
|---|---|---|---|---|
| KH-01 🔥 | Login berhasil, menu sesuai peran | EP | PJ, admin | Sapaan benar; menu PJ 3 item, admin 4 item |
| KH-02 | Login gagal | EP, Neg | sandi salah, email tidak terdaftar, kosong, huruf besar, spasi di sandi | "Email atau kata sandi salah", aplikasi tidak terbuka |
| KH-03 | Email tidak peka huruf besar dan spasi | EP | `  DEMO@KingsHand.id ` | Login berhasil |
| KH-04 | Tombol akun demo | – | Admin lembaga | Email dan sandi admin terisi |
| KH-05 | Halaman peran lain lewat URL | Neg | admin→proposal/profile, PJ→review/reports | Dialihkan ke Dashboard |
| KH-06 | Keluar | ST | – | Form login tampil, juga setelah muat ulang |

### Proposal: story [SCRUM-9](https://mochiii762025.atlassian.net/browse/SCRUM-9), [SCRUM-10](https://mochiii762025.atlassian.net/browse/SCRUM-10)

| ID | Skenario | Teknik | Data | Hasil yang diharapkan |
|---|---|---|---|---|
| KH-10 🔥 | Proposal baru sampai ke admin | E2E | Rp 75 juta, lampiran PDF | Status "Menunggu persetujuan"; admin melihat dana dan lampiran |
| KH-11 | Kolom wajib | EP | tanpa judul / lokasi / target / mulai / deskripsi | "Lengkapi semua kolom sebelum mengajukan." |
| KH-12 | Batas kebutuhan dana | BVA | 0, −5, kosong (ditolak); 1, 1500 (diterima) | Sesuai partisi |
| KH-13 | Dana ≥ 1.000 juta | – | 1500 | "Rp 1,5 miliar" |
| KH-14 | Ubah proposal menunggu | ST | judul dan dana baru | Perubahan terlihat di admin |
| KH-15 🔥 | Disetujui menjadi proyek aktif | ST, E2E | catatan admin | Penghitung berkurang, PJ dapat notifikasi + catatan, proyek di filter Aktif |
| KH-16 | Penolakan wajib alasan | ST, Neg | tanpa / dengan alasan | Tanpa: pesan wajib. Dengan: PJ melihat "Ditolak" + alasan, proyek hilang |
| KH-17 🔁 | Rencana mulai dipertahankan (BUG-04) | – | 3 bulan lagi | Bulan mulai proyek = rencana |
| KH-18 ⚠️ | Rencana mulai di masa lalu (BUG-05) | Neg | 2 tahun lalu | Pesan validasi |

### Laporan: story [SCRUM-11](https://mochiii762025.atlassian.net/browse/SCRUM-11), [SCRUM-12](https://mochiii762025.atlassian.net/browse/SCRUM-12)

| ID | Skenario | Teknik | Data | Hasil yang diharapkan |
|---|---|---|---|---|
| KH-20 🔥 | Laporan progres sampai ke admin | E2E | Cafe, 70% | Progres 70%, "Menunggu diperiksa", admin melihat "Progres 70%" |
| KH-21 | Validasi laporan progres | EP, BVA | tanpa file, kosong, 101, −1, 62 (< 63) | Pesan sesuai tiap kasus |
| KH-22 | Batas progres | BVA | 63 (sama), 100 diterima; 0 ditolak | Sesuai |
| KH-23 | Validasi laporan keuangan | BVA, Neg | 289 (< 290), −10 | Pesan sesuai |
| KH-24 | Proyek selesai | ST | Perpustakaan Keliling | Form laporan tidak ada |
| KH-25 | Admin menerima laporan | ST, E2E | Progres-Unit-Pilah.pdf | Pindah ke "Sudah diperiksa", PJ diberi tahu |
| KH-26 | Minta revisi | ST, Neg | tanpa / dengan catatan | Catatan wajib; PJ melihat peringatan revisi + catatan |
| KH-27 🔁 | Koreksi setelah revisi (BUG-01) | ST | 95% → revisi → 70% | Diterima; laporan berikutnya < 70% ditolak |
| KH-27b 🔁 | Koreksi tidak di bawah nilai sah (BUG-01) | BVA | 95% → revisi → 60% | "tidak boleh turun dari 63%" |
| KH-28 🔁 | Peringatan revisi hilang (BUG-02) | ST | laporan pengganti | Peringatan hilang, laporan lama "Diganti laporan baru" |
| KH-29 ⚠️ | Progres bilangan bulat (BUG-07) | Neg | 63.5 | Pesan validasi |

### Peringatan otomatis: story [SCRUM-13](https://mochiii762025.atlassian.net/browse/SCRUM-13)

| ID | Skenario | Teknik | Data | Hasil yang diharapkan |
|---|---|---|---|---|
| KH-30 🔥 | Peringatan awal | – | data contoh | "Perlu perhatian (2)": Klinik (83% vs 66%), SLB Papua (45 hari) |
| KH-31 | Batas selisih dana-progres | BVA | Cafe: Rp 385 juta (14 poin), Rp 390 juta (15 poin) | 14: tidak ada; 15: ada |
| KH-32 | Laporan menghapus peringatan terlambat | ST | SLB Papua, 60% | Peringatan terlambat hilang |
| KH-33 | Filter perlu perhatian (admin) | – | – | Angka 2; daftar berisi Klinik dan SLB Papua |
| KH-34 🔁 | Melebihi anggaran (BUG-03) | BVA | Bank Sampah: Rp 120 (100%), 121, 130 juta | 100%: tidak ada; 101%, 108%: ada |

### Pencarian, profil, notifikasi, ponsel: story [SCRUM-14](https://mochiii762025.atlassian.net/browse/SCRUM-14)

| ID | Skenario | Teknik | Data | Hasil yang diharapkan |
|---|---|---|---|---|
| KH-40 | Cari nama atau lokasi | EP | cafe, BANDUNG, garut | Daftar tepat |
| KH-41 | Tanpa hasil | Neg | zzz | "Tidak ada proyek yang cocok." |
| KH-42 ⚠️ | Tidak cocok lintas kolom (BUG-06) | Neg | cikutracikutra | Tanpa hasil |
| KH-43 | Ubah nama | – | Rina Sari | "Halo, Rina" |
| KH-44 | Email profil valid | EP, Neg | bukan-email, a@, @kingshand.id, kosong | Pesan validasi |
| KH-45 ⚠️ | Telepon hanya angka (BUG-08) | Neg | "bukan nomor" | Pesan validasi |
| KH-46 | Notifikasi dibaca | ST | admin | Penanda hilang, juga setelah muat ulang |
| KH-47 | Data tersimpan dan reset | ST | – | Bertahan setelah muat ulang, kembali awal setelah reset |
| KH-48 | Tampilan ponsel | – | 390 px: dashboard, proyek, proposal, detail, profil | Tanpa gulir horizontal |

## Restful-Booker (API)

### Autentikasi: story [SCRUM-15](https://mochiii762025.atlassian.net/browse/SCRUM-15)

| ID | Skenario | Teknik | Data | Hasil yang diharapkan |
|---|---|---|---|---|
| RB-01 🔥 | Health check | – | `GET /ping` | 201 |
| RB-02 🔥 | Login admin | – | admin / password123 | 200, token 15 hex |
| RB-03 | Token unik | – | dua login | Token berbeda |
| RB-04 | Login gagal tanpa token | EP, Neg | sandi salah, user salah, kosong | `{"reason": "Bad credentials"}` |
| RB-05 ⚠️ | Login gagal 401 (BUG-09) | Neg | sandi salah | 401 |

### CRUD booking: story [SCRUM-16](https://mochiii762025.atlassian.net/browse/SCRUM-16)

| ID | Skenario | Teknik | Data | Hasil yang diharapkan |
|---|---|---|---|---|
| RB-10 🔥 | Buat lalu baca | – | booking lengkap | 200; respons dan GET sama dengan yang dikirim; lolos JSON schema |
| RB-11 | additionalneeds opsional | EP | tanpa additionalneeds | Diterima, kolom tidak ada di respons |
| RB-12 | Nama dipangkas | – | `"  Rina  "` | "Rina" |
| RB-13 | Cari berdasarkan nama | – | firstname + lastname unik | Tepat satu id, milik booking itu |
| RB-14 | Booking tidak ada | Neg | id 99999999 | 404 |
| RB-15 | Header Accept | EP | JSON, XML, `*/*`, text/plain | 200 + format sesuai; text/plain 418 |
| RB-20 | PUT dengan token atau Basic auth | EP | cookie token, Authorization Basic | 200, data terganti |
| RB-21 | PATCH sebagian | – | additionalneeds | Hanya kolom itu berubah |
| RB-24 🔥 | Hapus | – | – | Berhasil; GET sesudahnya 404 |
| RB-25 ⚠️ | Kode status hapus (BUG-13) | – | – | 200 atau 204 |
| RB-26 ⚠️ | Ubah/hapus id tidak ada (BUG-14) | Neg | PUT, DELETE id 99999999 | 404 |

### Otorisasi dan validasi: story [SCRUM-17](https://mochiii762025.atlassian.net/browse/SCRUM-17)

| ID | Skenario | Teknik | Data | Hasil yang diharapkan |
|---|---|---|---|---|
| RB-22 | Tanpa login | Neg | PUT, PATCH, DELETE | 403; data tidak berubah |
| RB-23 | Token palsu | Neg | 15 nol | 403 |
| RB-30 | Kolom wajib hilang | EP, Neg | 6 kolom wajib, satu per satu | Ditolak (≥ 400) |
| RB-31 ⚠️ | Kode 400 untuk data tidak lengkap (BUG-10) | Neg | tanpa firstname, tanpa checkout | 400 |
| RB-32 | PUT data tidak lengkap | Neg | tanpa lastname | 400 |
| RB-33 | depositpaid boolean | EP | true, false | Tersimpan sama |
| RB-34 ⚠️ | depositpaid teks (BUG-11) | Neg | "false" | 400 atau false |
| RB-35 ⚠️ | Checkout sebelum checkin (BUG-12) | Neg | 10 Des → 1 Des | 400 |

## Koleksi Postman

Folder **1. Smoke** (9 request) menjalankan alur lengkap: ping → login → buat → baca → cari → PUT → PATCH → hapus → baca (404). Token dan bookingid diteruskan lewat collection variable. Folder **2. Negative** (5 request) berisi kredensial salah, tanpa login, token palsu, data tidak lengkap, dan Accept tidak didukung. Total 39 asersi, termasuk validasi JSON schema dan waktu respons < 3 detik.
