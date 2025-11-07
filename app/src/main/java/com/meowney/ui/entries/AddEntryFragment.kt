package com.meowney.ui.entries

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.meowney.databinding.FragmentAddEntryBinding


class AddEntryFragment : Fragment() {

    private var _binding: FragmentAddEntryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddEntryBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.addEntryToolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}