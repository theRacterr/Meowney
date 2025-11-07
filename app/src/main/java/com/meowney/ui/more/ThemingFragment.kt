package com.meowney.ui.more

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.meowney.R
import androidx.core.content.edit
import com.meowney.databinding.FragmentThemingBinding

class ThemingFragment : Fragment() {

    private var _binding: FragmentThemingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentThemingBinding.inflate(inflater, container, false)
        val view = binding.root

        // toolbar
        binding.themingToolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        // theme colors
        val prefs = requireContext().getSharedPreferences("theme_prefs", Context.MODE_PRIVATE)

        val colorOverlays = mapOf(
            R.id.colorGreen to R.style.ThemeOverlay_Green,
            R.id.colorRed to R.style.ThemeOverlay_Red
        )

        // TODO: migrate to view binding
        colorOverlays.forEach { (colorId, overlayId) ->
            view.findViewById<View>(colorId).setOnClickListener {
                prefs.edit { putInt("themeOverlay", overlayId) }
                requireActivity().recreate()
            }
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}