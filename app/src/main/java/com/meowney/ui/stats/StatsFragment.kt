package com.meowney.ui.stats

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.meowney.MainActivity
import com.meowney.R
import com.meowney.data.database.DatabaseProvider
import com.meowney.data.repositories.AccountRepository
import com.meowney.data.repositories.GeneralTransactionRepository
import com.meowney.data.repositories.TransactionCategoryRepository
import com.meowney.databinding.FragmentStatsBinding
import kotlinx.coroutines.launch


class StatsFragment : Fragment() {

    private var _binding: FragmentStatsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStatsBinding.inflate(inflater, container, false)
        val view = binding.root

        val viewModel: StatsViewModel by viewModels()

        binding.statsToolbar.setNavigationOnClickListener {
            (activity as MainActivity).openDrawer()
        }

        val db = DatabaseProvider.getDatabase(requireContext())
        val transactionRepository = GeneralTransactionRepository(db.generalTransactionDao())
        val categoryRepository = TransactionCategoryRepository(db.transactionCategoryDao())
        val accountRepository = AccountRepository(db.accountDao())

        binding.accountName.setOnClickListener {
            lifecycleScope.launch {
                showAccountDialog(accountRepository.getAllAccountNames(), onAccountSelected = { selectedAccount ->
                    viewModel.setSelectedAccount(selectedAccount)
                    reloadStats(viewModel, transactionRepository, categoryRepository, accountRepository)
                })
            }
        }


        reloadStats(viewModel, transactionRepository, categoryRepository, accountRepository)
        
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showAccountDialog(accountNames: Array<String>, onAccountSelected: (Int) -> Unit) {

        val accountOptions = arrayOf(getString(R.string.all_accounts)) + accountNames

        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.select_account)
            .setItems(accountOptions) { dialog, which ->
                onAccountSelected(which)
                dialog.dismiss()
            }
            .show()
    }

    private fun reloadStats(viewModel: StatsViewModel, transactionRepository: GeneralTransactionRepository, categoryRepository: TransactionCategoryRepository, accountRepository: AccountRepository) {

        lifecycleScope.launch {

            var totalBalance: Double?

            if (viewModel.selectedAccount.value == 0) {
                totalBalance = transactionRepository.getSumOfAll()
                binding.accountName.text = getString(R.string.all_accounts)
            } else {
                totalBalance = transactionRepository.getSumByAccountId(viewModel.selectedAccount.value)
                binding.accountName.text = accountRepository.getNameById(viewModel.selectedAccount.value)
            }

            if (totalBalance == null) {
                totalBalance = 0.0
            }

            binding.accountBalance.text = totalBalance.toString()
        }

        // TODO: add stats
    }
}