# Test plan

| | |
|---|---|
| Penyusun | M. Rivaldi Mahari |
| Versi | 1.0, November 2026 |
| Aplikasi | KINGSHANDs web (aplikasi monitoring proyek pendanaan) dan Restful-Booker API |
| Pelacakan | Jira, project QA Testing (SCRUM) |

## 1. Tujuan

1. Memastikan alur utama KINGSHANDs berjalan untuk kedua peran: penanggung jawab (PJ) mengajukan proposal dan melapor, admin menyetujui dan memeriksa laporan.
2. Memastikan peringatan otomatis muncul tepat pada batas aturannya.
3. Memeriksa API Restful-Booker: autentikasi, CRUD booking, validasi, dan kode status.
4. Menemukan dan melaporkan cacat, memverifikasi perbaikannya, dan menjaganya dengan test regresi.

## 2. Ruang lingkup

**Termasuk**

| Area | Fitur |
|---|---|
| KINGSHANDs: login dan hak akses | Login, akun demo, menu per peran, pembatasan halaman lewat URL, keluar |
| KINGSHANDs: proposal | Pengajuan, validasi form, ubah proposal, persetujuan, penolakan, notifikasi |
| KINGSHANDs: laporan | Laporan progres dan keuangan, validasi, pemeriksaan admin, permintaan revisi |
| KINGSHANDs: peringatan | Dana lebih cepat dari progres, laporan terlambat, revisi, melebihi anggaran |
| KINGSHANDs: lain-lain | Pencarian dan filter proyek, profil, notifikasi, penyimpanan data, tampilan ponsel |
| Restful-Booker | `/ping`, `/auth`, `/booking` (GET, POST, PUT, PATCH, DELETE), header Accept |

**Tidak termasuk**

- Uji beban dan keamanan mendalam (penetration test).
- Browser selain Chromium (Firefox dan Safari dicatat sebagai risiko).
- Versi desktop KINGSHANDs (JavaFX).
- Instance publik Restful-Booker sebagai target utama, karena datanya dipakai bersama dan sering di-reset. Test API dijalankan pada instance lokal dengan versi yang dikunci; instance publik bisa dipakai lewat `RB_URL`.

## 3. Pendekatan

| Teknik | Dipakai untuk |
|---|---|
| Pengujian berbasis requirement | Satu atau lebih test case per aturan bisnis ([test-cases.md](test-cases.md)) |
| Equivalence partitioning | Kredensial login, format email, kolom wajib |
| Boundary value analysis | Batas peringatan dana (selisih 14 vs 15 poin), anggaran 100% vs 101%, progres 0 / 63 / 100 / 101 |
| State transition | Proposal: menunggu → disetujui / ditolak; laporan: menunggu → diterima / perlu revisi → diganti |
| Pengujian eksploratif | Sesi berbatas waktu dengan charter ([exploratory-testing.md](exploratory-testing.md)) |
| Negative testing (API) | Kredensial salah, tanpa token, data tidak lengkap, tipe data salah, resource tidak ada |
| Regresi otomatis | Setiap bug yang diperbaiki mendapat test yang sudah dibuktikan gagal di versi lama |

**Otomasi**

- UI: Playwright + pytest dengan Page Object Model (`ui/pages/`). Setiap test memakai konteks browser baru sehingga data contoh selalu mulai dari awal.
- API: pytest + requests dengan validasi skema JSON, ditambah koleksi Postman yang dijalankan Newman.
- Bug yang masih terbuka tetap ditulis sebagai test dengan perilaku yang benar, ditandai `xfail` strict. Jika bug diperbaiki, test itu menjadi XPASS dan pipeline gagal, sehingga penandanya harus dilepas dan tiket Jira ditutup.

## 4. Lingkungan

| Komponen | Detail |
|---|---|
| KINGSHANDs | Versi terbaru dari `rivaldimhr/KINGSHANDs`, disajikan lokal (`KH_DIR`) atau dari situs online (`KH_URL`) |
| Restful-Booker | `mwinteringham/restful-booker` commit `619d031`, Node 24, port 3001 |
| Browser | Chromium (Playwright), desktop 1280×860 dan ponsel 390×844 |
| CI | GitHub Actions setiap push dan setiap hari pukul 06.00 WIB |
| Data uji | Data contoh bawaan KINGSHANDs (lihat `ui/data.py`); booking API dibuat dengan nama unik dan dihapus setelah test |

## 5. Kriteria

**Masuk:** aplikasi bisa dibuka, akun demo bisa login, Restful-Booker menjawab `/ping`.

**Selesai:**

- Semua test smoke lulus.
- Tidak ada bug terbuka dengan severity High.
- Setiap bug terbuka tercatat di Jira dan punya test `xfail`.
- Setiap bug yang diperbaiki punya test regresi.

## 6. Severity dan prioritas

| Severity | Arti | Contoh |
|---|---|---|
| High | Alur utama tidak bisa diselesaikan atau data penting salah, tanpa jalan keluar | Progres yang salah tidak bisa dikoreksi |
| Medium | Informasi menyesatkan atau aturan bisnis salah, tetapi ada jalan keluar | Proyek melebihi anggaran ditandai aman |
| Low | Validasi kurang atau kosmetik, dampak kecil | Nomor telepon menerima huruf |

## 7. Risiko

| Risiko | Mitigasi |
|---|---|
| Data contoh KINGSHANDs relatif terhadap tanggal hari ini | Test menghitung tanggal secara dinamis (`ui/data.py`) |
| Instance publik Restful-Booker lambat atau di-reset | Instance lokal dengan versi dikunci di CI |
| Hanya Chromium yang diuji | Dicatat; bisa ditambah dengan `browser_type` Playwright lain |
| Requirement tidak tertulis lengkap | Perilaku yang ambigu dicatat sebagai pertanyaan, bukan bug (lihat bug-reports.md) |

## 8. Hasil yang diserahkan

- Test case: [test-cases.md](test-cases.md)
- Catatan pengujian eksploratif: [exploratory-testing.md](exploratory-testing.md)
- Laporan bug: [bug-reports.md](bug-reports.md), dan tiketnya di Jira
- Test otomatis, laporan HTML, dan pipeline CI
