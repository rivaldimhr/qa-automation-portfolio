package kingshand

import java.time.YearMonth

/**
 * Data uji KINGSHANDs. Nilai proyek berasal dari data contoh aplikasi (lihat docs/test-plan.md di root repo).
 */
class KhData {

	// id proyek contoh
	static final int CAFE = 1            // anggaran Rp 500 juta, progres 63%, terpakai Rp 290 juta
	static final int SLB_PAPUA = 2       // laporan perkembangan terakhir 45 hari lalu
	static final int BANK_SAMPAH = 4     // anggaran Rp 120 juta, progres 90%, terpakai Rp 100 juta
	static final int PERPUSTAKAAN = 5    // proyek selesai
	static final int KLINIK = 6          // terpakai 83%, progres 66%

	static final String SUMUR_BOR = 'Sumur Bor Desa Cikadu'   // proposal contoh yang menunggu persetujuan

	/** Bulan ke-n dari sekarang dalam format input type="month" (yyyy-MM). */
	static String monthValue(int offset) {
		return YearMonth.now().plusMonths(offset).toString()
	}

	/** Proposal valid; kolom tertentu bisa diganti atau dihapus. */
	static Map proposal(Map overrides = [:]) {
		Map data = [
			judul    : 'Taman Baca Anak Cimahi',
			lokasi   : 'Cimahi',
			target   : 'Siswa SD',
			dana     : '75',
			mulai    : monthValue(2),
			deskripsi: 'Taman baca dengan 500 buku untuk anak SD di Cimahi.'
		]
		data.putAll(overrides)
		return data
	}

	static Map proposalWithout(String field) {
		Map data = proposal()
		data.remove(field)
		return data
	}
}
