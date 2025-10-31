package com.meowney.ui.more

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.appbar.MaterialToolbar
import com.meowney.R

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

        return view
    }
}