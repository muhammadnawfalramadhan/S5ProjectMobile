package com.nawfal.mnr_martabak.Riwayat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.datepicker.MaterialDatePicker
import com.nawfal.mnr_martabak.R
import com.nawfal.mnr_martabak.databinding.FragmentRiwayatBinding

class RiwayatFragment : Fragment() {

    private var _binding: FragmentRiwayatBinding? = null
    private val binding get() = _binding!!

    private val semuaTransaksi = DataTransaksi.contoh()
    private lateinit var adapter: TransaksiAdapter

    // null = tidak ada filter (tampilkan semua transaksi)
    private var tanggalDipilih: Long? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRiwayatBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val activity = requireActivity() as AppCompatActivity
        activity.setSupportActionBar(binding.toolbar)
        activity.supportActionBar?.title = getString(R.string.menu_riwayat)

        adapter = TransaksiAdapter(semuaTransaksi)
        binding.rvTransaksi.layoutManager = LinearLayoutManager(requireContext())
        binding.rvTransaksi.adapter = adapter

        binding.btnTanggal.setOnClickListener { tampilkanDatePicker() }
        binding.btnReset.setOnClickListener {
            tanggalDipilih = null
            perbaruiDaftar()
        }

        perbaruiDaftar()
    }

    private fun tampilkanDatePicker() {
        val picker = MaterialDatePicker.Builder.datePicker()
            .setTitleText(getString(R.string.riwayat_judul_picker))
            .setSelection(tanggalDipilih ?: MaterialDatePicker.todayInUtcMilliseconds())
            .build()

        // Dipanggil saat tombol OK ditekan; selection = tanggal pilihan (milidetik UTC)
        picker.addOnPositiveButtonClickListener { selection ->
            tanggalDipilih = selection
            perbaruiDaftar()
        }

        // Di dalam Fragment pakai parentFragmentManager (bukan supportFragmentManager)
        picker.show(parentFragmentManager, "DATE_PICKER")
    }

    private fun perbaruiDaftar() {
        val tanggal = tanggalDipilih
        val hasil = if (tanggal == null) {
            semuaTransaksi
        } else {
            semuaTransaksi.filter { it.tanggalUtc == tanggal }
        }
        adapter.setData(hasil)

        binding.txtFilter.text = if (tanggal == null) {
            getString(R.string.riwayat_semua)
        } else {
            getString(R.string.riwayat_filter, formatTanggal(tanggal))
        }
        binding.btnReset.visibility = if (tanggal == null) View.GONE else View.VISIBLE
        binding.txtKosong.visibility = if (hasil.isEmpty()) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}