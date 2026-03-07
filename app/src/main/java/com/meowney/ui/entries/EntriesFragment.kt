package com.meowney.ui.entries

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.meowney.MainActivity
import com.meowney.R
import com.meowney.data.database.DatabaseProvider
import com.meowney.data.database.entities.GeneralTransaction
import com.meowney.data.repositories.AccountRepository
import com.meowney.data.repositories.GeneralTransactionRepository
import com.meowney.data.repositories.TransactionCategoryRepository
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

        val viewModel: EntriesViewModel by viewModels()

        binding.entriesToolbar.setNavigationOnClickListener {
            (activity as MainActivity).openDrawer()
        }

        binding.entriesRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        // TODO: move to viewModel for performance
        val db = DatabaseProvider.getDatabase(requireContext())
        val transactionRepository = GeneralTransactionRepository(db.generalTransactionDao())
        val categoryRepository = TransactionCategoryRepository(db.transactionCategoryDao())
        val accountRepository = AccountRepository(db.accountDao())

        lifecycleScope.launch {
            // TODO: limit and re query when scrolling for better performance

            val categories = categoryRepository.getAllCategories()

            var transactions: List<GeneralTransaction>
            var totalBalance: Double?

            if (viewModel.selectedAccount.value == 0) {
                transactions = transactionRepository.getAllTransactions()
                totalBalance = transactionRepository.getSumOfAll()
                binding.accountName.text = getString(R.string.all_accounts)
            } else {
                transactions = transactionRepository.getAllTransactionsByAccountId(viewModel.selectedAccount.value)
                totalBalance = transactionRepository.getSumByAccountId(viewModel.selectedAccount.value)
                binding.accountName.text = accountRepository.getNameById(viewModel.selectedAccount.value)
            }

            if (totalBalance == null) {
                totalBalance = 0.0
            }

            binding.accountBalance.text = totalBalance.toString()

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