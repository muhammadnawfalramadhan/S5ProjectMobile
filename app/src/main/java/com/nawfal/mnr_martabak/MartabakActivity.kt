package com.nawfal.mnr_martabak

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.nawfal.mnr_martabak.databinding.ActivityMartabakBinding

class MartabakActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMartabakBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMartabakBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = getString(R.string.btn_my_project)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}