package com.nawfal.mnr_martabak

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.nawfal.mnr_martabak.databinding.ActivitySplashScreenBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Tampilkan versi aplikasi dari versionName di build.gradle
        val versi = packageManager.getPackageInfo(packageName, 0).versionName
        binding.txtVersi.text = getString(R.string.versi_app, versi)

        lifecycleScope.launch {
            delay(2000) // simulasi pengambilan data selama 2 detik

            // Pengecekan sesi login dipindahkan ke sini (sebelumnya di AuthActivity)
            val sharedPref = getSharedPreferences("user_pref", MODE_PRIVATE)
            val isLogin = sharedPref.getBoolean("isLogin", false)

            // isLogin true -> MainActivity, false -> AuthActivity
            val tujuan = if (isLogin) MainActivity::class.java else AuthActivity::class.java
            val intent = Intent(this@SplashScreenActivity, tujuan)
            startActivity(intent)
            finish()
        }
    }
}
