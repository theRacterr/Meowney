package com.meowney.ui.more

import android.content.Context
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
import androidx.core.content.edit


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

        binding.rowColorMode.setOnClickListener {
            showColorModeDialog()
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
            .setTitle(R.string.dialog_select_language)
            .setSingleChoiceItems(languages, selectedIndex) { _, which ->
                selectedIndex = which
            }
            .setPositiveButton(R.string.dialog_confirm) { dialog, _ ->
                val selectedLanguage = languageCodes[selectedIndex]

                val appLocale: LocaleListCompat = LocaleListCompat.forLanguageTags(selectedLanguage)
                AppCompatDelegate.setApplicationLocales(appLocale)

                dialog.dismiss()
            }
            .setNegativeButton(R.string.dialog_cancel, null)
            .show()
    }

    private fun showColorModeDialog() {
        val colorModes = resources.getStringArray(R.array.color_mode_options)
        val colorModeValues = arrayOf(AppCompatDelegate.MODE_NIGHT_NO, AppCompatDelegate.MODE_NIGHT_YES, AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)

        val sharedPref = requireActivity().getSharedPreferences("MeowneyPrefs", Context.MODE_PRIVATE)
        val currentNightMode = sharedPref.getInt("night_mode", AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        var checkedItem = colorModeValues.indexOf(currentNightMode)

        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.dialog_color_mode)
            .setSingleChoiceItems(colorModes, checkedItem) { _, which ->
                checkedItem = which
            }
            .setPositiveButton(R.string.dialog_confirm) { dialog, _ ->
                val selectedMode = colorModeValues[checkedItem]

                sharedPref.edit { putInt("night_mode", selectedMode) }

                AppCompatDelegate.setDefaultNightMode(selectedMode)
                dialog.dismiss()
            }
            .setNegativeButton(R.string.dialog_cancel, null)
            .show()
    }
}