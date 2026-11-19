package com.nawfal.mnr_martabak.Home

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

// Satu kotak menu pada grid Home
data class MenuHome(
    val id: String,
    @StringRes val judul: Int,
    @DrawableRes val ikon: Int,
    @ColorRes val warna: Int
)
