package com.nawfal.mnr_martabak.Pinjaman

import com.nawfal.mnr_martabak.Riwayat.utcMillis

// Satu baris jadwal angsuran pinjaman
data class Angsuran(
    val ke: Int,
    val jatuhTempoUtc: Long,
    val nominal: Long,
    val lunas: Boolean
)

// Data contoh (dummy) pinjaman aktif anggota
object DataPinjaman {
    const val CICILAN: Long = 450_000
    const val TENOR = 12            // lama pinjaman (bulan)
    const val SUDAH_DIBAYAR = 3     // sama dengan "Angsuran pinjaman ke-1..3" di Riwayat

    // Jatuh tempo setiap tanggal 12, mulai September 2026
    fun jadwal(): List<Angsuran> = (1..TENOR).map { ke ->
        val urutanBulan = 8 + ke                  // 9 = September 2026
        val tahun = 2026 + (urutanBulan - 1) / 12
        val bulan = (urutanBulan - 1) % 12 + 1
        Angsuran(ke, utcMillis(tahun, bulan, 12), CICILAN, ke <= SUDAH_DIBAYAR)
    }

    fun totalPinjaman(): Long = CICILAN * TENOR

    fun sisaPinjaman(): Long = jadwal().filter { !it.lunas }.sumOf { it.nominal }
}
