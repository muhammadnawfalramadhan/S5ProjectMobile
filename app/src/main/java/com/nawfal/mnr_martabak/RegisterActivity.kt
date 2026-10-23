package com.nawfal.mnr_martabak

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.nawfal.mnr_martabak.databinding.ActivityRegisterBinding

// Quiz Soal 2: halaman Registrasi
class RegisterActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_EMAIL = "extra_email"
    }

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Email otomatis terisi dari halaman Gmail dan tidak bisa diubah (enabled=false di XML)
        val email = intent.getStringExtra(EXTRA_EMAIL).orEmpty()
        binding.inputEmail.setText(email)

        binding.btnRegistrasi.setOnClickListener {
            val nama = binding.inputNama.text.toString().trim()
            val username = binding.inputUsername.text.toString().trim()
            val password = binding.inputPassword.text.toString().trim()

            val pesanError = when {
                nama.isEmpty() || email.isEmpty() || username.isEmpty() || password.isEmpty() ->
                    getString(R.string.semua_wajib_diisi)
                password.length < 6 -> getString(R.string.password_min_6)
                username.contains(" ") -> getString(R.string.username_tanpa_spasi)
                else -> null
            }

            if (pesanError != null) {
                MaterialAlertDialogBuilder(this)
                    .setTitle(getString(R.string.registrasi_gagal))
                    .setMessage(pesanError)
                    .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
                    .show()
                return@setOnClickListener
            }

            // Simpan seluruh data registrasi ke SharedPreferences
            val sharedPref = getSharedPreferences("user_pref", MODE_PRIVATE)
            val editor = sharedPref.edit()
            editor.putString("reg_nama", nama)
            editor.putString("reg_email", email)
            editor.putString("reg_username", username)
            editor.putString("reg_password", password)
            editor.apply()

            val intent = Intent(this, RegisterSuccessActivity::class.java)
            intent.putExtra(RegisterSuccessActivity.EXTRA_NAMA, nama)
            intent.putExtra(RegisterSuccessActivity.EXTRA_USERNAME, username)
            startActivity(intent)
            finish()
        }
    }
}
