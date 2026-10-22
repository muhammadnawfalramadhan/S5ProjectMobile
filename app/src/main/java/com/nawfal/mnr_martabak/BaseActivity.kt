package com.nawfal.mnr_martabak

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.nawfal.mnr_martabak.Home.HomeFragment
import com.nawfal.mnr_martabak.Pinjaman.PinjamanFragment
import com.nawfal.mnr_martabak.Profile.ProfileFragment
import com.nawfal.mnr_martabak.Riwayat.RiwayatFragment
import com.nawfal.mnr_martabak.Simpanan.SimpananFragment
import com.nawfal.mnr_martabak.databinding.ActivityBaseBinding

class BaseActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBaseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBaseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // HomeFragment sebagai fragment default (hanya saat pertama dibuat)
        if (savedInstanceState == null) {
            replaceFragment(HomeFragment())
        }

        binding.bottomNavView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> { replaceFragment(HomeFragment()); true }
                R.id.simpanan -> { replaceFragment(SimpananFragment()); true }
                R.id.pinjaman -> { replaceFragment(PinjamanFragment()); true }
                R.id.riwayat -> { replaceFragment(RiwayatFragment()); true }
                R.id.profil -> { replaceFragment(ProfileFragment()); true }
                else -> false
            }
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(binding.fragmentContainer.id, fragment)
            //.addToBackStack(null) -> dinonaktifkan agar tombol back langsung keluar aplikasi
            .commit()
    }
}