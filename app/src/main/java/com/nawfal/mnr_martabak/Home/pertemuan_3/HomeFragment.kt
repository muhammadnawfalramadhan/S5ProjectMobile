package com.nawfal.mnr_martabak.Home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.nawfal.mnr_martabak.AuthActivity
import com.nawfal.mnr_martabak.BaseActivity
import com.nawfal.mnr_martabak.Home.pertemuan_3.ThirdActivity
import com.nawfal.mnr_martabak.MartabakActivity
import com.nawfal.mnr_martabak.Pinjaman.DataPinjaman
import com.nawfal.mnr_martabak.R
import com.nawfal.mnr_martabak.Riwayat.DataTransaksi
import com.nawfal.mnr_martabak.Riwayat.Jenis
import com.nawfal.mnr_martabak.Riwayat.TransaksiAdapter
import com.nawfal.mnr_martabak.Riwayat.formatRupiah
import com.nawfal.mnr_martabak.WebActivity
import com.nawfal.mnr_martabak.databinding.FragmentHomeBinding
import com.nawfal.mnr_martabak.onboarding.OnboardingActivity

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    // Daftar menu yang tampil di grid Home
    private val daftarMenu = listOf(
        MenuHome("simpanan", R.string.grid_simpanan, R.drawable.ic_simpanan, R.color.siksp_hijau_muda),
        MenuHome("pinjaman", R.string.grid_pinjaman, R.drawable.ic_pinjaman, R.color.siksp_biru_muda),
        MenuHome("riwayat", R.string.grid_riwayat, R.drawable.ic_riwayat, R.color.siksp_ungu_muda),
        MenuHome("profil", R.string.grid_profil, R.drawable.ic_profil, R.color.siksp_kuning),
        MenuHome("web", R.string.grid_info, R.drawable.ic_info, R.color.siksp_biru_muda),
        MenuHome("panduan", R.string.grid_panduan, R.drawable.ic_panduan, R.color.siksp_hijau_muda),
        MenuHome("martabak", R.string.grid_martabak, R.drawable.ic_makanan, R.color.siksp_kuning),
        MenuHome("latihan", R.string.grid_latihan, R.drawable.ic_kode, R.color.siksp_ungu_muda)
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val activity = requireActivity() as AppCompatActivity
        activity.setSupportActionBar(binding.toolbar)
        activity.supportActionBar?.title = getString(R.string.judul_app_1)

        val sharedPref = requireContext().getSharedPreferences("user_pref", Context.MODE_PRIVATE)
        val username = sharedPref.getString("username", "")
        binding.txtSapaan.text = getString(R.string.sapaan, username)

        tampilkanSaldo()
        pasangGridMenu()
        pasangAktivitasTerakhir()

        binding.btnLogout.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle(getString(R.string.btn_logout))
                .setMessage(getString(R.string.konfirmasi_logout))
                .setPositiveButton("Ya") { dialog, _ ->
                    sharedPref.edit().clear().apply()
                    dialog.dismiss()
                    startActivity(Intent(requireContext(), AuthActivity::class.java))
                    requireActivity().finish()
                }
                .setNegativeButton("Tidak") { dialog, _ -> dialog.dismiss() }
                .show()
        }
    }

    private fun tampilkanSaldo() {
        // Saldo simpanan = total setoran - total penarikan
        val transaksi = DataTransaksi.contoh()
        val setoran = transaksi.filter { it.jenis == Jenis.SETORAN }.sumOf { it.nominal }
        val penarikan = transaksi.filter { it.jenis == Jenis.PENARIKAN }.sumOf { it.nominal }

        binding.txtSaldo.text = formatRupiah(setoran - penarikan)
        binding.txtSisaPinjaman.text = formatRupiah(DataPinjaman.sisaPinjaman())
    }

    private fun pasangGridMenu() {
        val adapter = MenuHomeAdapter(daftarMenu) { menu -> bukaMenu(menu) }
        binding.rvMenu.layoutManager = GridLayoutManager(requireContext(), 4) // 4 kolom
        binding.rvMenu.adapter = adapter
    }

    private fun pasangAktivitasTerakhir() {
        // 3 transaksi terbaru saja, daftar lengkapnya ada di tab Riwayat
        binding.rvAktivitas.layoutManager = LinearLayoutManager(requireContext())
        binding.rvAktivitas.adapter = TransaksiAdapter(DataTransaksi.contoh().take(3))
        binding.btnLihatSemua.setOnClickListener { bukaTab(R.id.riwayat) }
    }

    private fun bukaMenu(menu: MenuHome) {
        when (menu.id) {
            "simpanan" -> bukaTab(R.id.simpanan)
            "pinjaman" -> bukaTab(R.id.pinjaman)
            "riwayat" -> bukaTab(R.id.riwayat)
            "profil" -> bukaTab(R.id.profil)
            "web" -> startActivity(Intent(requireContext(), WebActivity::class.java))
            "panduan" -> {
                val intent = Intent(requireContext(), OnboardingActivity::class.java)
                intent.putExtra(OnboardingActivity.EXTRA_DARI_HOME, true)
                startActivity(intent)
            }
            "martabak" -> startActivity(Intent(requireContext(), MartabakActivity::class.java))
            "latihan" -> startActivity(Intent(requireContext(), ThirdActivity::class.java))
        }
    }

    // Pindah tab lewat BottomNavigationView milik BaseActivity
    private fun bukaTab(itemId: Int) {
        (requireActivity() as BaseActivity).bukaTab(itemId)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
