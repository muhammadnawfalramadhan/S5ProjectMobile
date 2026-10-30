package com.nawfal.mnr_martabak.Riwayat

import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone

// Jenis transaksi. masuk = true artinya menambah saldo anggota
enum class Jenis(val label: String, val masuk: Boolean) {
    SETORAN("Setoran", true),
    PENARIKAN("Penarikan", false),
    ANGSURAN("Angsuran", false)
}

// tanggalUtc disimpan dalam milidetik UTC (jam 00:00), sama dengan format hasil DatePicker nanti
data class Transaksi(
    val jenis: Jenis,
    val keterangan: String,
    val tanggalUtc: Long,
    val nominal: Long
)

private val UTC = TimeZone.getTimeZone("UTC")
private val LOKAL_ID: Locale = Locale.forLanguageTag("id-ID")

fun utcMillis(tahun: Int, bulan: Int, tanggal: Int): Long {
    val kalender = Calendar.getInstance(UTC)
    kalender.clear()
    kalender.set(tahun, bulan - 1, tanggal)
    return kalender.timeInMillis
}

fun formatTanggal(millisUtc: Long): String {
    val format = SimpleDateFormat("dd MMM yyyy", LOKAL_ID)
    format.timeZone = UTC
    return format.format(Date(millisUtc))
}

fun formatRupiah(nominal: Long): String {
    return "Rp " + NumberFormat.getInstance(LOKAL_ID).format(nominal)
}

// Data contoh (dummy), diurutkan dari yang terbaru
object DataTransaksi {
    fun contoh(): List<Transaksi> = listOf(
        Transaksi(Jenis.SETORAN, "Simpanan sukarela", utcMillis(2026, 10, 28), 300_000),
        Transaksi(Jenis.ANGSURAN, "Angsuran pinjaman ke-3", utcMillis(2026, 10, 26), 450_000),
        Transaksi(Jenis.SETORAN, "Simpanan wajib", utcMillis(2026, 10, 20), 100_000),
        Transaksi(Jenis.PENARIKAN, "Penarikan simpanan sukarela", utcMillis(2026, 10, 15), 200_000),
        Transaksi(Jenis.ANGSURAN, "Angsuran pinjaman ke-2", utcMillis(2026, 10, 12), 450_000),
        Transaksi(Jenis.SETORAN, "Simpanan wajib", utcMillis(2026, 10, 5), 100_000),
        Transaksi(Jenis.SETORAN, "Simpanan pokok", utcMillis(2026, 10, 5), 500_000),
        Transaksi(Jenis.ANGSURAN, "Angsuran pinjaman ke-1", utcMillis(2026, 9, 12), 450_000),
        Transaksi(Jenis.SETORAN, "Simpanan wajib", utcMillis(2026, 9, 5), 100_000)
    )
}