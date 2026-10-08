package com.nawfal.mnr_martabak.pertemuan_3

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.nawfal.mnr_martabak.databinding.ActivityThirdResultBinding

class ThirdResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityThirdResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityThirdResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}