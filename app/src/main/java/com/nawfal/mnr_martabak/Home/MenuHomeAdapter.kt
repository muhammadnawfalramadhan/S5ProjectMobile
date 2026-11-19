package com.nawfal.mnr_martabak.Home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.nawfal.mnr_martabak.databinding.ItemMenuHomeBinding

class MenuHomeAdapter(
    private val daftarMenu: List<MenuHome>,
    private val onMenuClick: (MenuHome) -> Unit
) : RecyclerView.Adapter<MenuHomeAdapter.MenuViewHolder>() {

    // ViewHolder menyimpan view satu kotak menu supaya bisa dipakai ulang (recycle)
    class MenuViewHolder(val binding: ItemMenuHomeBinding) : RecyclerView.ViewHolder(binding.root)

    // 1. Membuat kotak menu baru dari item_menu_home.xml
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val binding = ItemMenuHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MenuViewHolder(binding)
    }

    // 2. Mengisi kotak menu dengan data menu ke-position
    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        val menu = daftarMenu[position]
        val context = holder.itemView.context

        holder.binding.imgIkon.setImageResource(menu.ikon)
        holder.binding.imgIkon.setColorFilter(ContextCompat.getColor(context, menu.warna))
        holder.binding.txtJudul.setText(menu.judul)
        holder.binding.root.setOnClickListener { onMenuClick(menu) }
    }

    // 3. Jumlah kotak menu
    override fun getItemCount(): Int = daftarMenu.size
}
