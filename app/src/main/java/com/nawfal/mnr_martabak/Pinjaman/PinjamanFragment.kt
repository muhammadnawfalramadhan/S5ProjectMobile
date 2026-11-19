package com.nawfal.mnr_martabak.Pinjaman

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.nawfal.mnr_martabak.R
import com.nawfal.mnr_martabak.Riwayat.formatRupiah
import com.nawfal.mnr_martabak.Riwayat.formatTanggal
import com.nawfal.mnr_martabak.databinding.FragmentPinjamanBinding

class PinjamanFragment : Fragment() {

    private var _binding: FragmentPinjamanBinding? = null
    private val binding get() = _binding!!

    private val jadwal = DataPinjaman.jadwal()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPinjamanBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val activity = requireActivity() as AppCompatActivity
        activity.setSupportActionBar(binding.toolbar)
        activity.supportActionBar?.title = getString(R.string.menu_pinjaman)

        // Ringkasan pinjaman
        val jumlahLunas = jadwal.count { it.lunas }
        binding.txtTotalPinjaman.text = formatRupiah(DataPinjaman.totalPinjaman())
        binding.txtCicilan.text = getString(
            R.string.pinjaman_cicilan, formatRupiah(DataPinjaman.CICILAN), DataPinjaman.TENOR
        )
        binding.progresAngsuran.max = jadwal.size
        binding.progresAngsuran.progress = jumlahLunas
        binding.txtProgres.text = getString(R.string.pinjaman_progres, jumlahLunas, jadwal.size)

        // Pasang CustomAdapter ke ListView
        binding.lvAngsuran.adapter = JadwalAngsuranAdapter(jadwal)
        binding.lvAngsuran.setOnItemClickListener { _, _, position, _ ->
            val angsuran = jadwal[position]
            val status = getString(if (angsuran.lunas) R.string.status_lunas else R.string.status_belum)
            Toast.makeText(
                requireContext(),
                getString(R.string.toast_angsuran, angsuran.ke, formatTanggal(angsuran.jatuhTempoUtc), status),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
