# Pengujian eksploratif

Pengujian eksploratif dilakukan sebelum menulis test otomatis, dengan sesi berbatas waktu (session-based test management). Setiap sesi punya *charter*: area, tujuan, dan pertanyaan yang ingin dijawab.

## Sesi 1: alur laporan dan revisi

| | |
|---|---|
| Charter | Jelajahi alur laporan PJ → pemeriksaan admin → revisi, untuk menemukan keadaan yang membuat PJ "terkunci" |
| Waktu | 45 menit |
| Peran | PJ dan admin bergantian |
| Heuristik | *Undo/correction*: setelah kesalahan, apakah pengguna bisa memperbaikinya? *State*: apa yang terjadi pada status lama ketika status baru datang? |

**Catatan**

- PJ melaporkan progres 95% untuk Cafe (sebelumnya 63%), seolah salah ketik. Admin meminta revisi: "progres sebenarnya 70%".
- PJ mencoba mengirim 70%: ditolak, "Progres tidak boleh turun dari 95%". Nilai yang sedang diminta revisi ternyata dipakai sebagai batas bawah. **→ BUG-01 (High)**
- PJ hanya bisa mengirim ≥ 95%. Setelah admin menerima laporan baru itu, peringatan "perlu revisi" untuk laporan lama tetap tampil di dashboard dan halaman proyek. **→ BUG-02 (Medium)**
- Validasi file kosong, nilai > 100, dan nilai negatif sudah benar.
- Progres `63.5` diterima dan tampil "63.5%", padahal kolom memakai `step="1"`. **→ BUG-07 (Low)**

## Sesi 2: peringatan otomatis dan anggaran

| | |
|---|---|
| Charter | Uji batas aturan peringatan dana dan laporan |
| Waktu | 30 menit |
| Heuristik | *Boundary*: tepat di batas, satu di bawah, satu di atas. *Goldilocks*: terlalu kecil, pas, terlalu besar |

**Catatan**

- Selisih dana-progres 14 poin: tidak ada peringatan. 15 poin: ada peringatan. Sesuai aturan (≥ 15).
- Bank Sampah (anggaran Rp 120 juta): progres 100%, dana terpakai Rp 130 juta (108%). Halaman menulis "Proyek ini aman". Selisih dengan progres hanya 8 poin, jadi aturan (a) tidak terpicu, dan tidak ada aturan lain untuk dana di atas anggaran. **→ BUG-03 (Medium)**
- Laporan perkembangan baru pada SLB Papua menghapus peringatan "terlambat". Benar.

## Sesi 3: proposal dan data tanggal

| | |
|---|---|
| Charter | Jelajahi data proposal sepanjang hidupnya: diisi PJ, dibaca admin, menjadi proyek |
| Waktu | 30 menit |
| Heuristik | *CRUD consistency*: apakah data yang diisi tetap sama di setiap tahap? *Time*: masa lalu, sekarang, masa depan |

**Catatan**

- Rencana mulai 3 bulan lagi tampil benar di kartu evaluasi admin. Setelah disetujui, bulan mulai proyek berubah menjadi bulan persetujuan; rencana PJ hilang tanpa pemberitahuan. **→ BUG-04 (Medium)**
- Rencana mulai Januari 2019 diterima tanpa peringatan. **→ BUG-05 (Low)**
- Dana Rp 1.500 juta ditulis "Rp 1,5 miliar". Benar.
- Proposal bisa diajukan tanpa lampiran PDF; admin melihat "Lampiran: Tidak ada". Label form tidak menyebut lampiran wajib atau opsional. **→ Pertanyaan ke pemilik produk, bukan bug** (Q-01 di bug-reports.md).

## Sesi 4: pencarian, profil, dan tampilan ponsel

| | |
|---|---|
| Charter | Cari input yang diterima padahal seharusnya tidak, dan kebalikannya |
| Waktu | 20 menit |

**Catatan**

- Pencarian `cikutracikutra` menemukan Bank Sampah Cikutra (nama "...Cikutra" + lokasi "Cikutra, ..."). Nama dan lokasi digabung tanpa pemisah sebelum dicocokkan. **→ BUG-06 (Low)**
- Nomor telepon "bukan nomor" tersimpan. **→ BUG-08 (Low)**
- Email `a@`, `@kingshand.id`, dan kosong ditolak. Benar.
- Halaman dashboard, proyek, proposal, detail, dan profil tidak melebar di layar 390 px.

## Ringkasan

| Sesi | Bug ditemukan | Pertanyaan |
|---|---|---|
| 1 | BUG-01, BUG-02, BUG-07 | – |
| 2 | BUG-03 | – |
| 3 | BUG-04, BUG-05 | Q-01 |
| 4 | BUG-06, BUG-08 | – |

Setiap temuan diulang (reproduksi) dengan script Playwright sebelum dilaporkan, dan screenshot buktinya ada di [img/bugs/](img/bugs/).
