package com.nawfal.mnr_martabak

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.nawfal.mnr_martabak.databinding.ActivityAuthBinding

class AuthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Kode ini harus selalu dipanggil saat butuh akses "user_pref"
        val sharedPref = getSharedPreferences("user_pref", MODE_PRIVATE)

        // Catatan: pengecekan isLogin sudah dipindahkan ke SplashScreenActivity

        binding.btnLogin.setOnClickListener {
            val username = binding.inputUsername.text.toString().trim()
            val password = binding.inputPassword.text.toString().trim()

            // Data hasil registrasi (Quiz Soal 2)
            val regUsername = sharedPref.getString("reg_username", null)
            val regPassword = sharedPref.getString("reg_password", null)

            // Login berhasil jika salah satu terpenuhi:
            // 1. username == password
            // 2. username & password sesuai data registrasi di SharedPreferences
            val loginLama = username.isNotEmpty() && username == password
            val loginRegistrasi = regUsername != null &&
                username == regUsername && password == regPassword

            if (loginLama || loginRegistrasi) {
                val editor = sharedPref.edit()
                editor.putBoolean("isLogin", true)
                editor.putString("username", username)
                editor.apply()

                // Arahkan ke halaman Home (BaseActivity dengan bottom navigation)
                val intent = Intent(this, BaseActivity::class.java)
                startActivity(intent)
                finish() // agar tombol back dari Home tidak kembali ke halaman login
            } else {
                MaterialAlertDialogBuilder(this)
                    .setTitle(getString(R.string.login_gagal))
                    .setMessage(getString(R.string.username_password_salah))
                    .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
                    .show()
            }
        }

        // Quiz Soal 1: Register With Gmail
        binding.btnRegisterGmail.setOnClickListener {
            startActivity(Intent(this, EmailActivity::class.java))
        }
    }
}
