package com.meowney.ui.stats

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.meowney.MainActivity
import com.meowney.databinding.FragmentStatsBinding


class StatsFragment : Fragment() {

    private var _binding: FragmentStatsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStatsBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.statsToolbar.setNavigationOnClickListener {
            (activity as MainActivity).openDrawer()
        }
        
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}