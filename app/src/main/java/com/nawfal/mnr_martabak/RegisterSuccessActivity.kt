package com.nawfal.mnr_martabak

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.nawfal.mnr_martabak.databinding.ActivityRegisterSuccessBinding

// Quiz Soal 2: halaman informasi "Registrasi Berhasil"
class RegisterSuccessActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_NAMA = "extra_nama"
        const val EXTRA_USERNAME = "extra_username"
    }

    private lateinit var binding: ActivityRegisterSuccessBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterSuccessBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val nama = intent.getStringExtra(EXTRA_NAMA).orEmpty()
        val username = intent.getStringExtra(EXTRA_USERNAME).orEmpty()
        binding.tvSubjudul.text = getString(R.string.info_registrasi, nama, username)

        binding.btnKeLogin.setOnClickListener {
            // Kembali ke halaman Login & hapus halaman Email/Registrasi dari back stack
            val intent = Intent(this, AuthActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
            finish()
        }
    }
}
