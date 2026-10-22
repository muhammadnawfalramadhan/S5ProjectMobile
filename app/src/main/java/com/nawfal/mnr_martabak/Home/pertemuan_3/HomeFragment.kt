package com.nawfal.mnr_martabak.Home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.nawfal.mnr_martabak.AuthActivity
import com.nawfal.mnr_martabak.Home.pertemuan_3.ThirdActivity
import com.nawfal.mnr_martabak.MartabakActivity
import com.nawfal.mnr_martabak.R
import com.nawfal.mnr_martabak.WebActivity
import com.nawfal.mnr_martabak.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val activity = requireActivity() as AppCompatActivity
        activity.setSupportActionBar(binding.toolbar)
        activity.supportActionBar?.title = getString(R.string.menu_beranda)

        val sharedPref = requireContext().getSharedPreferences("user_pref", Context.MODE_PRIVATE)

        val username = sharedPref.getString("username", "")
        binding.txtSapaan.text = getString(R.string.sapaan, username)

        binding.btnMyProject.setOnClickListener {
            startActivity(Intent(requireContext(), MartabakActivity::class.java))
        }
        binding.btnPertemuan3.setOnClickListener {
            startActivity(Intent(requireContext(), ThirdActivity::class.java))
        }
        binding.btnWeb.setOnClickListener {
            startActivity(Intent(requireContext(), WebActivity::class.java))
        }

        binding.btnLogout.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle(getString(R.string.btn_logout))
                .setMessage(getString(R.string.konfirmasi_logout))
                .setPositiveButton("Ya") { dialog, _ ->
                    sharedPref.edit().clear().apply()
                    dialog.dismiss()
                    startActivity(Intent(requireContext(), AuthActivity::class.java))
                    requireActivity().finish()
                }
                .setNegativeButton("Tidak") { dialog, _ -> dialog.dismiss() }
                .show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}