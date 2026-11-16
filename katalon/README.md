# Project Katalon Studio: KINGSHANDs (Web UI) dan Restful-Booker (API)

Pengujian yang sama dengan bagian Playwright/pytest dan Postman di repo ini, dibuat ulang dengan **Katalon Studio**. ID test case (KH-xx, RB-xx) sama dengan [test case](../docs/test-cases.md) dan tiket Jira, jadi setiap skenario bisa ditelusuri dari requirement sampai hasil eksekusi di dua alat.

| | |
|---|---|
| Test case | 42: 28 Web UI (KINGSHANDs), 14 API (Restful-Booker) |
| Regresi | 36 test case (24 UI, 12 API), termasuk regresi BUG-01 dan BUG-03 yang gagal bila dijalankan ke versi KINGSHANDs sebelum perbaikan |
| Bug terbuka | 6 test di suite **Known Bugs** (BUG-05, 06, 07, 08, 09, 10), sengaja gagal sampai bugnya diperbaiki |
| Object Repository | 61 objek web (sebagian berparameter `${...}`) dan 8 objek web service |
| Data-driven | 3 Data File CSV: login gagal, batas dana proposal, validasi laporan progres |

## Membuka dan menjalankan

1. Clone repo ini, atau di Katalon Studio pilih **File → Git → Clone Project** dengan URL `https://github.com/rivaldimhr/qa-automation-portfolio.git`.
2. **File → Open Project**, pilih folder `katalon/` (berisi `KingsHand-Katalon.prj`).
3. Pilih profil di pojok kanan atas (**Profiles**):
   - `default`: versi online, [KINGSHANDs di GitHub Pages](https://rivaldimhr.github.io/kingshand/) dan `restful-booker.herokuapp.com`. Bisa langsung dijalankan.
   - `local`: server lokal (`http://localhost:8000/` dan `http://localhost:3001`), dipakai juga di CI.
4. Buka **Test Suites**, pilih suite, lalu **Run → Chrome**. Laporan HTML ada di folder `Reports/`.

Menjalankan aplikasi secara lokal (untuk profil `local`):

```bash
git clone https://github.com/rivaldimhr/KINGSHANDs && (cd KINGSHANDs/web && python -m http.server 8000)
git clone https://github.com/mwinteringham/restful-booker && (cd restful-booker && npm ci && npm start)
```

## Test Suite

| Suite | Isi | Hasil yang diharapkan |
|---|---|---|
| `UI/Smoke UI` | KH-01, KH-10, KH-15, KH-20, KH-30 | Lulus |
| `UI/Regression UI` | 24 test case UI | Lulus |
| `API/Smoke API` | RB-01, RB-02, RB-10, RB-24 | Lulus |
| `API/Regression API` | 12 test case API | Lulus |
| `Known Bugs` | 4 UI + 2 API untuk bug yang masih terbuka | **Gagal** (lihat di bawah) |
| `Regression Lengkap` | Test Suite Collection: Regression UI lalu Regression API | Lulus |

**Kenapa ada suite yang sengaja gagal?** Katalon tidak punya penanda `xfail` seperti pytest. Jadi test untuk bug yang belum diperbaiki ditulis dengan perilaku yang **benar** dan dikumpulkan di suite `Known Bugs`, terpisah dari regresi. Selama bug ada, test-nya gagal. Kalau suatu saat ada yang lulus, berarti bugnya sudah diperbaiki: pindahkan test ke suite regresi dan tutup tiket Jira-nya. Setiap test di suite ini diberi tag `known-bug` dan ID bug.

## Struktur

```
KingsHand-Katalon.prj        file project Katalon
Profiles/                    default (online) dan local: alamat aplikasi, akun demo, timeout
Object Repository/
  KINGSHANDs/                objek web per halaman: Login, Header, Dashboard, Projects, Proposal, Review, Reports, Detail, Profile
  RestfulBooker/             objek web service: GET_Ping, POST_Auth, POST_CreateBooking, GET_Booking, GET_SearchBooking,
                             PUT_UpdateBooking, PATCH_PartialUpdateBooking, DELETE_Booking
Keywords/
  kingshand/KH.groovy        custom keyword UI: openApp, loginAs, switchTo, submitProposal, sendReport, approveProposal,
                             requestRevision, editProfile, verifyContains, ...
  kingshand/KhData.groovy    data uji: id proyek contoh, proposal valid, bulan relatif
  restfulbooker/Booker.groovy  custom keyword API: token, createBooking, updateBooking, patchBooking, deleteBooking, ...
Test Cases/ + Scripts/       UI/01 Login ... 05 Pencarian dan profil, 99 Bug terbuka; API/01 Autentikasi ... 99 Bug terbuka
Data Files/ + Include/data/  data-driven test (CSV)
Test Suites/                 smoke, regresi, known bugs, dan Test Suite Collection
Test Listeners/              screenshot saat test UI gagal, browser selalu ditutup setelah test UI
```

## Fitur Katalon yang dipakai

- **Object Repository berparameter.** Satu objek untuk banyak elemen serupa, misalnya `Review/btn_Setujui` dengan XPath `//article[...][h3='${judul}']//button[@data-approve]`, dipanggil `findTestObject('KINGSHANDs/Review/btn_Setujui', [('judul') : 'Sumur Bor Desa Cikadu'])`.
- **Web service request object** dengan variabel (`${baseUrl}`, `${id}`, `${token}`, `${authorization}`, `${body}`), header cookie token dan Basic auth, serta verifikasi `WS.verifyResponseStatusCode` dan `WS.verifyElementPropertyValue`.
- **Custom Keywords** (`CustomKeywords.'kingshand.KH.loginAs'('pj')`), supaya langkah yang sama (login, ganti peran, kirim laporan) tidak ditulis ulang di setiap test case. Keyword `verifyContains` dan `verifyText` membungkus `WebUI.verifyMatch`/`verifyEqual`, sehingga pesan gagal di log Katalon menyebut teks aktual dan yang diharapkan.
- **Data-driven testing** dengan Data File CSV dan `findTestData(...)`, misalnya KH-12 menguji batas dana 0, −5, kosong, 1, dan 1500.
- **Profiles** untuk berganti lingkungan (online atau lokal) tanpa mengubah test case.
- **Test Listener** (`@BeforeTestCase`, `@AfterTestCase`) untuk screenshot saat gagal dan menutup browser, sehingga setiap test case mulai dari browser dan data demo yang bersih.
- **Test Suite Collection** untuk menjalankan UI dan API berurutan.

Dua hal teknis dari aplikasi yang ditangani di keyword:

- Input file di KINGSHANDs disembunyikan (`display:none`) di balik label bergaya tombol. `KH.chooseFile` menampilkannya dulu lewat JavaScript, lalu memakai `WebUI.uploadFile` dengan file PDF kecil yang dibuat dengan nama sesuai skenario.
- Input `type="month"` (rencana mulai) diisi lewat JavaScript karena format ketiknya berbeda-beda menurut locale Chrome.

## CI dengan Katalon Runtime Engine

Workflow [`katalon.yml`](../.github/workflows/katalon.yml) menjalankan `Regression Lengkap` dan `Known Bugs` dengan Katalon Runtime Engine (image Docker `katalonstudio/katalon`) terhadap KINGSHANDs dan Restful-Booker lokal, lalu mengunggah folder `Reports` sebagai artifact. Katalon Runtime Engine butuh API key akun Katalon: simpan di **Settings → Secrets and variables → Actions** dengan nama `KATALON_API_KEY`. Tanpa secret itu, workflow hanya memberi catatan dan tidak gagal. Perintah yang sama bisa dijalankan sendiri dengan [`scripts/run-katalon.sh`](../scripts/run-katalon.sh).
