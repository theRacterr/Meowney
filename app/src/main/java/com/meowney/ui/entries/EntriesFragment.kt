package com.meowney.ui.entries

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.meowney.MainActivity
import com.meowney.data.database.entities.GeneralTransaction
import com.meowney.data.database.entities.TransactionCategory
import com.meowney.databinding.FragmentEntriesBinding


class EntriesFragment : Fragment() {

    private var _binding: FragmentEntriesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEntriesBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.entriesToolbar.setNavigationOnClickListener {
            (activity as MainActivity).openDrawer()
        }

        // TODO: Remove sample data
        val sampleCategory: MutableList<TransactionCategory> = mutableListOf()
        sampleCategory.add(TransactionCategory(1, "sample1", "ic_eye_black_24dp"))

        val sampleData: MutableList<GeneralTransaction> = mutableListOf()
        sampleData.add(GeneralTransaction(1, 1, 1, "sample1", "sample1", 120.0, "10.1.2025"))
        sampleData.add(GeneralTransaction(2, 1, 1, "sample2", "sample2", 100.0, "12.2.2025"))
        sampleData.add(GeneralTransaction(3, 1, 1, "sample3", "sample3", -150.0, "1.4.2025"))
        sampleData.add(GeneralTransaction(4, 1, 1, "sample4", "sample4", 0.0, "15.6.2025"))

        binding.entriesRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        val adapter = EntriesAdapter(sampleData, sampleCategory)
        binding.entriesRecyclerView.adapter = adapter

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}