package com.nawfal.mnr_martabak.Simpanan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.nawfal.mnr_martabak.R
import com.nawfal.mnr_martabak.databinding.FragmentSimpananBinding

class SimpananFragment : Fragment() {

    private var _binding: FragmentSimpananBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSimpananBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val activity = requireActivity() as AppCompatActivity
        activity.setSupportActionBar(binding.toolbar)
        activity.supportActionBar?.title = getString(R.string.menu_simpanan)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}