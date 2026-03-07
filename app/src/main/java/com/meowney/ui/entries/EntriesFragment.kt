package com.meowney.ui.entries

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.meowney.MainActivity
import com.meowney.data.database.DatabaseProvider
import com.meowney.databinding.FragmentEntriesBinding
import kotlinx.coroutines.launch


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

        binding.entriesRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        val db = DatabaseProvider.getDatabase(requireContext())

        lifecycleScope.launch {
            val transactions = db.generalTransactionDao().getAll() // TODO: limit and re query when scrolling for better performance
            val categories = db.transactionCategoryDao().getAll()

            val adapter = EntriesAdapter(transactions, categories)
            binding.entriesRecyclerView.adapter = adapter
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}