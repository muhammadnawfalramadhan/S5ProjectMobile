package com.nawfal.mnr_martabak.Riwayat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.nawfal.mnr_martabak.R
import com.nawfal.mnr_martabak.databinding.ItemTransaksiBinding

class TransaksiAdapter(
    private var daftar: List<Transaksi>
) : RecyclerView.Adapter<TransaksiAdapter.TransaksiViewHolder>() {

    // ViewHolder memegang view satu baris supaya tidak dicari berulang-ulang
    class TransaksiViewHolder(val binding: ItemTransaksiBinding) : RecyclerView.ViewHolder(binding.root)

    // 1. Membuat baris baru dari item_transaksi.xml
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransaksiViewHolder {
        val binding = ItemTransaksiBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TransaksiViewHolder(binding)
    }

    // 2. Mengisi baris dengan data transaksi ke-position
    override fun onBindViewHolder(holder: TransaksiViewHolder, position: Int) {
        val transaksi = daftar[position]
        val context = holder.itemView.context

        val warna = when (transaksi.jenis) {
            Jenis.SETORAN -> R.color.siksp_hijau_muda
            Jenis.PENARIKAN -> R.color.siksp_merah
            Jenis.ANGSURAN -> R.color.siksp_biru_muda
        }
        val tanda = if (transaksi.jenis.masuk) "+" else "-"

        holder.binding.txtJenis.text = transaksi.jenis.label
        holder.binding.txtJenis.setTextColor(ContextCompat.getColor(context, warna))
        holder.binding.txtKeterangan.text = transaksi.keterangan
        holder.binding.txtTanggal.text = formatTanggal(transaksi.tanggalUtc)
        holder.binding.txtNominal.text = "$tanda ${formatRupiah(transaksi.nominal)}"
        holder.binding.txtNominal.setTextColor(ContextCompat.getColor(context, warna))
    }

    // 3. Berapa banyak baris
    override fun getItemCount(): Int = daftar.size

    // Dipakai nanti saat hasil filter berubah
    fun setData(dataBaru: List<Transaksi>) {
        daftar = dataBaru
        notifyDataSetChanged()
    }
}