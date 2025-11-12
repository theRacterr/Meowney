package com.meowney.ui.entries

import android.content.res.ColorStateList
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import androidx.navigation.fragment.findNavController
import com.google.android.material.color.MaterialColors
import com.meowney.R
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

        binding.radioGroupType.setOnCheckedChangeListener { _, checkedId ->
            var colorAttr = R.attr.toolbarElementColor
            when (checkedId) {
                R.id.radioIncome -> {
                    binding.imagePosNeg.setImageResource(R.drawable.ic_plus_black_24dp)
                    colorAttr = R.attr.profitColor
                }
                R.id.radioExpense -> {
                    binding.imagePosNeg.setImageResource(R.drawable.ic_minus_black_24dp)
                    colorAttr = R.attr.deficitColor
                }
                R.id.radioTransfer -> {
                    binding.imagePosNeg.setImageResource(R.drawable.ic_tilde_black_24dp)
                }
            }
            val color = MaterialColors.getColor(requireContext(), colorAttr, ContextCompat.getColor(requireContext(), R.color.black))
            ImageViewCompat.setImageTintList(binding.imagePosNeg, ColorStateList.valueOf(color))
        }
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}