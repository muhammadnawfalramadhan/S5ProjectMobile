package com.nawfal.mnr_martabak.Pinjaman

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.core.content.ContextCompat
import com.nawfal.mnr_martabak.R
import com.nawfal.mnr_martabak.Riwayat.formatRupiah
import com.nawfal.mnr_martabak.Riwayat.formatTanggal
import com.nawfal.mnr_martabak.databinding.ItemAngsuranBinding

// CustomAdapter untuk ListView: turunan BaseAdapter yang menghubungkan
// List<Angsuran> dengan tampilan item_angsuran.xml
class JadwalAngsuranAdapter(
    private val daftar: List<Angsuran>
) : BaseAdapter() {

    override fun getCount(): Int = daftar.size

    override fun getItem(position: Int): Angsuran = daftar[position]

    override fun getItemId(position: Int): Long = daftar[position].ke.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        // convertView != null berarti ada baris lama yang bisa dipakai ulang,
        // jadi tidak perlu inflate layout lagi (lebih hemat)
        val binding = if (convertView == null) {
            val bindingBaru = ItemAngsuranBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            bindingBaru.root.tag = bindingBaru
            bindingBaru
        } else {
            convertView.tag as ItemAngsuranBinding
        }

        val angsuran = daftar[position]
        val context = parent.context

        binding.txtNomor.text = angsuran.ke.toString()
        binding.txtJudul.text = context.getString(R.string.angsuran_ke, angsuran.ke)
        binding.txtJatuhTempo.text = context.getString(R.string.angsuran_jatuh_tempo, formatTanggal(angsuran.jatuhTempoUtc))
        binding.txtNominal.text = formatRupiah(angsuran.nominal)

        val warnaStatus = if (angsuran.lunas) R.color.siksp_hijau else R.color.siksp_kuning
        binding.txtStatus.setText(if (angsuran.lunas) R.string.status_lunas else R.string.status_belum)
        binding.txtStatus.backgroundTintList =
            ColorStateList.valueOf(ContextCompat.getColor(context, warnaStatus))

        return binding.root
    }
}
