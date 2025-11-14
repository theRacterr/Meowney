package com.meowney.ui.more

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.meowney.R
import com.meowney.data.SettingsDataStore
import com.meowney.databinding.FragmentPrivacyBinding
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking


class PrivacyFragment : Fragment() {

    private var _binding: FragmentPrivacyBinding? = null
    private val binding get() = _binding!!

    private val settingsDataStore by lazy { SettingsDataStore(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPrivacyBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.privacyToolbar.setNavigationOnClickListener {
            findNavController().navigate(R.id.navigation_more)
        }

        runBlocking {
            binding.privacySwitch.isChecked = settingsDataStore.privacyMode.first()
        }

        binding.privacySwitch.setOnCheckedChangeListener { _, isChecked ->
            runBlocking {
                settingsDataStore.savePrivacyMode(isChecked)
            }
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}