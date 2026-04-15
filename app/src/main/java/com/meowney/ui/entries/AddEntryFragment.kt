package com.meowney.ui.entries

import android.content.res.ColorStateList
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.color.MaterialColors
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.meowney.R
import com.meowney.data.database.DatabaseProvider
import com.meowney.data.database.entities.GeneralTransaction
import com.meowney.data.repositories.AccountRepository
import com.meowney.data.repositories.GeneralTransactionRepository
import com.meowney.data.repositories.TransactionCategoryRepository
import com.meowney.databinding.FragmentAddEntryBinding
import kotlinx.coroutines.launch
import kotlin.getValue


class AddEntryFragment : Fragment() {

    private var _binding: FragmentAddEntryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddEntryBinding.inflate(inflater, container, false)
        val view = binding.root

        val viewModel: AddEntryViewModel by viewModels()

        // TODO: move to viewModel for performance
        val db = DatabaseProvider.getDatabase(requireContext())
        val transactionRepository = GeneralTransactionRepository(db.generalTransactionDao())
        val categoryRepository = TransactionCategoryRepository(db.transactionCategoryDao())
        val accountRepository = AccountRepository(db.accountDao())

        binding.addEntryToolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        // updating GUI based on selected account and category
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.selectedAccount.collect { accountId ->
                val name = accountRepository.getNameById(accountId)
                binding.accountSelector.text = name
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.selectedCategory.collect { categoryId ->
                val name = categoryRepository.getCategoryById(categoryId).name
                binding.categorySelector.text = name
            }
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
            }
            val color = MaterialColors.getColor(requireContext(), colorAttr, ContextCompat.getColor(requireContext(), R.color.black))
            ImageViewCompat.setImageTintList(binding.imagePosNeg, ColorStateList.valueOf(color))
        }

        binding.accountSelector.setOnClickListener {
            lifecycleScope.launch {
                changeAccountDialog(accountRepository.getAllAccountNames(),
                    onAccountSelected = {
                        viewModel.setSelectedAccount(it+1)
                })
            }
        }

        binding.categorySelector.setOnClickListener {
            lifecycleScope.launch {
                changeCategoryDialog(categoryRepository.getAllCategoryNames(),
                    onCategorySelected = {
                        viewModel.setSelectedCategory(it + 1)
                })
            }
        }

        binding.confirmButton.setOnClickListener {

            if (binding.editAmount.text.toString().isEmpty()) {
                Toast.makeText(
                    requireContext(),
                    R.string.input_a_number,
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }
            if (binding.editTitle.text.toString().isEmpty()) {
                Toast.makeText(
                    requireContext(),
                    R.string.input_a_title,
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            lifecycleScope.launch {

                val title = binding.editTitle.text.toString()
                var amount = binding.editAmount.text.toString().toDouble()
                if (binding.radioGroupType.checkedRadioButtonId == R.id.radioExpense) {
                    amount *= -1
                }

                transactionRepository.insertTransaction(
                    GeneralTransaction(

                        id = 0,
                        accountId = viewModel.selectedAccount.value,
                        categoryId = viewModel.selectedCategory.value,
                        title = title,
                        description = null,
                        amount = amount,
                        date = System.currentTimeMillis().toString()

                    )
                )

                findNavController().popBackStack()
            }
        }


        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun changeAccountDialog(accountNames: Array<String>, onAccountSelected: (Int) -> Unit) {
        val accountOptions = accountNames

        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.select_account)
            .setItems(accountOptions) { dialog, which ->
                onAccountSelected(which)
                dialog.dismiss()
            }
            .show()
    }

    private fun changeCategoryDialog(categoryNames: Array<String>, onCategorySelected: (Int) -> Unit) {
        val categoryOptions = categoryNames

        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.select_account)
            .setItems(categoryOptions) { dialog, which ->
                onCategorySelected(which)
                dialog.dismiss()
            }
            .show()
    }
}