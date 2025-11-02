package com.meowney.ui.more

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.appbar.MaterialToolbar
import com.meowney.R
import androidx.core.content.edit

class ThemingFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_theming, container, false)

        val toolbar = view.findViewById<MaterialToolbar>(R.id.themingToolbar)
        val navController = findNavController()
        toolbar.setNavigationOnClickListener {
            navController.navigateUp()
        }

        val prefs = requireContext().getSharedPreferences("theme_prefs", Context.MODE_PRIVATE)

        val colorOverlays = mapOf(
            R.id.colorGreen to R.style.ThemeOverlay_Green
        )

        colorOverlays.forEach { (colorId, overlayId) ->
            view.findViewById<View>(colorId).setOnClickListener {
                prefs.edit { putInt("themeOverlay", overlayId) }
                requireActivity().recreate()
            }
        }

        return view
    }
}