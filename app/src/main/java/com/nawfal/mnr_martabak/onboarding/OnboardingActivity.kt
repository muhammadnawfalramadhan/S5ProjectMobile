package com.nawfal.mnr_martabak.onboarding

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.nawfal.mnr_martabak.AuthActivity
import com.nawfal.mnr_martabak.BaseActivity
import com.nawfal.mnr_martabak.R
import com.nawfal.mnr_martabak.databinding.ActivityOnboardingBinding

class OnboardingActivity : AppCompatActivity() {

    companion object {
        // Disimpan di "app_pref" (bukan "user_pref") supaya tidak ikut terhapus saat logout
        const val PREF_APP = "app_pref"
        const val KEY_ONBOARDING_SELESAI = "onboarding_selesai"
        const val EXTRA_DARI_HOME = "dari_home"
    }

    private lateinit var binding: ActivityOnboardingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 1. Siapkan 3 halaman onboarding lalu pasang ke ViewPager2 lewat adapter
        val fragments = listOf(Onboarding1Fragment(), Onboarding2Fragment(), Onboarding3Fragment())
        binding.viewPagerOnboarding.adapter = OnboardingAdapter(this, fragments)

        // 2. Hubungkan DotsIndicator dengan ViewPager2
        binding.dotIndicator.attachTo(binding.viewPagerOnboarding)

        // 3. Ubah tombol sesuai halaman yang sedang aktif
        binding.viewPagerOnboarding.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                val halamanTerakhir = position == fragments.lastIndex
                binding.btnLanjut.setText(if (halamanTerakhir) R.string.onboard_mulai else R.string.onboard_lanjut)
                binding.btnLewati.visibility = if (halamanTerakhir) View.INVISIBLE else View.VISIBLE
            }
        })

        binding.btnLanjut.setOnClickListener {
            val posisi = binding.viewPagerOnboarding.currentItem
            if (posisi < fragments.lastIndex) {
                binding.viewPagerOnboarding.currentItem = posisi + 1
            } else {
                selesaiOnboarding()
            }
        }
        binding.btnLewati.setOnClickListener { selesaiOnboarding() }
    }

    private fun selesaiOnboarding() {
        // Tandai onboarding sudah pernah dilihat agar tidak muncul lagi setelah Splash
        getSharedPreferences(PREF_APP, MODE_PRIVATE).edit()
            .putBoolean(KEY_ONBOARDING_SELESAI, true)
            .apply()

        // Kalau dibuka dari menu Panduan di Home, cukup tutup dan kembali ke Home
        val dariHome = intent.getBooleanExtra(EXTRA_DARI_HOME, false)
        if (!dariHome) {
            val isLogin = getSharedPreferences("user_pref", MODE_PRIVATE).getBoolean("isLogin", false)
            val tujuan = if (isLogin) BaseActivity::class.java else AuthActivity::class.java
            startActivity(Intent(this, tujuan))
        }
        finish()
    }
}
