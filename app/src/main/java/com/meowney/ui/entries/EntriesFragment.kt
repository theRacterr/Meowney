package com.meowney.ui.entries

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.meowney.MainActivity
import com.meowney.R
import com.meowney.data.database.DatabaseProvider
import com.meowney.data.database.entities.Account
import com.meowney.data.database.entities.GeneralTransaction
import com.meowney.data.database.entities.TransactionCategory
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

        reloadEntries(viewModel, transactionRepository, categoryRepository, accountRepository)


        // Handles deleting with a swipe
        val swipeHandler = object : androidx.recyclerview.widget.ItemTouchHelper.SimpleCallback(
            0,
            androidx.recyclerview.widget.ItemTouchHelper.LEFT or androidx.recyclerview.widget.ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: androidx.recyclerview.widget.RecyclerView,
                viewHolder: androidx.recyclerview.widget.RecyclerView.ViewHolder,
                target: androidx.recyclerview.widget.RecyclerView.ViewHolder
            ): Boolean = false

            override fun onSwiped(viewHolder: androidx.recyclerview.widget.RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.bindingAdapterPosition
                if (position == androidx.recyclerview.widget.RecyclerView.NO_POSITION) return

                val adapter = binding.entriesRecyclerView.adapter as? EntriesAdapter ?: return
                val transactionToDelete = adapter.getItemAt(position)

                lifecycleScope.launch {
                    transactionRepository.deleteTransaction(transactionToDelete)
                    reloadEntries(viewModel, transactionRepository, categoryRepository, accountRepository)

                    com.google.android.material.snackbar.Snackbar.make(
                        binding.root,
                        R.string.entry_deleted,
                        com.google.android.material.snackbar.Snackbar.LENGTH_LONG
                    ).setAction(R.string.undo) {
                        lifecycleScope.launch {
                            transactionRepository.insertTransaction(transactionToDelete)
                            reloadEntries(viewModel, transactionRepository, categoryRepository, accountRepository)
                        }
                    }.show()
                }
            }
        }

        val itemTouchHelper = androidx.recyclerview.widget.ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(binding.entriesRecyclerView)

        // handles account switching
        binding.accountName.setOnClickListener {
            lifecycleScope.launch {
                showAccountDialog(accountRepository.getAllAccountNames(), onAccountSelected = { selectedAccount ->
                    viewModel.setSelectedAccount(selectedAccount)
                    reloadEntries(viewModel, transactionRepository, categoryRepository, accountRepository)
                })
            }
        }

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

    // TODO: move to viewModel for performance
    private fun reloadEntries(viewModel: EntriesViewModel, transactionRepository: GeneralTransactionRepository, categoryRepository: TransactionCategoryRepository, accountRepository: AccountRepository) {
        lifecycleScope.launch {
            // TODO: limit and re query when scrolling for better performance

            val categories = categoryRepository.getAllCategories()

            var transactions: List<GeneralTransaction>
            var totalBalance: Double?

            if (viewModel.selectedAccount.value == 0) {
                transactions = transactionRepository.getAllTransactions()
                totalBalance = transactionRepository.getSumOfAll()
                binding.accountName.text = getString(R.string.all_accounts)
                binding.accountCurrency.text = "EUR"
            } else {
                transactions = transactionRepository.getAllTransactionsByAccountId(viewModel.selectedAccount.value)
                totalBalance = transactionRepository.getSumByAccountId(viewModel.selectedAccount.value)
                binding.accountName.text = accountRepository.getNameById(viewModel.selectedAccount.value)
                binding.accountCurrency.text = accountRepository.getCurrencyById(viewModel.selectedAccount.value)
            }

            if (totalBalance == null) {
                totalBalance = 0.0
            }

            binding.accountBalance.text = totalBalance.toString()

            val adapter = EntriesAdapter(transactions.reversed(), categories)
            binding.entriesRecyclerView.adapter = adapter
        }
    }
}