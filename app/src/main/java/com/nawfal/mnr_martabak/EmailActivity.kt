package com.nawfal.mnr_martabak

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.nawfal.mnr_martabak.databinding.ActivityEmailBinding

// Quiz Soal 1: halaman input email (Register With Gmail)
class EmailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEmailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEmailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLanjut.setOnClickListener {
            val email = binding.inputEmail.text.toString().trim()

            // Validasi: tidak boleh kosong & harus domain @gmail.com
            val pesanError = when {
                email.isEmpty() -> getString(R.string.email_kosong)
                !Patterns.EMAIL_ADDRESS.matcher(email).matches() ||
                    !email.endsWith("@gmail.com", ignoreCase = true) ->
                    getString(R.string.email_bukan_gmail)
                else -> null
            }

            if (pesanError != null) {
                MaterialAlertDialogBuilder(this)
                    .setTitle(getString(R.string.email_tidak_valid))
                    .setMessage(pesanError)
                    .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
                    .show()
            } else {
                // Email dibawa ke halaman Registrasi
                val intent = Intent(this, RegisterActivity::class.java)
                intent.putExtra(RegisterActivity.EXTRA_EMAIL, email)
                startActivity(intent)
            }
        }
    }
}
