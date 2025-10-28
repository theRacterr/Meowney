package com.meowney.ui.more

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.meowney.R
import com.meowney.databinding.FragmentMoreBinding


class MoreFragment : Fragment() {
    private var _binding: FragmentMoreBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMoreBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.rowLanguage.setOnClickListener {
            showLanguageDialog()
        }

        binding.rowAbout.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_more_to_aboutFragment)
        }
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showLanguageDialog() {
        val languages = arrayOf("English", "Česky")
        val languageCodes = arrayOf("en", "cs")
        var selectedIndex = 0

        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Select Language")
            .setSingleChoiceItems(languages, selectedIndex) { _, which ->
                selectedIndex = which
            }
            .setPositiveButton("OK") { dialog, _ ->
                val selectedLanguage = languageCodes[selectedIndex]

                val appLocale: LocaleListCompat = LocaleListCompat.forLanguageTags(selectedLanguage)
                AppCompatDelegate.setApplicationLocales(appLocale)

                dialog.dismiss()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
}