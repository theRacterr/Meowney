package com.meowney.ui.entries

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class AddEntryViewModel : ViewModel() {

    private val _selectedAccount = MutableStateFlow(1)

    val selectedAccount: StateFlow<Int> get() = _selectedAccount.asStateFlow()

    fun setSelectedAccount(accountId: Int) {
        _selectedAccount.value = accountId
    }

    private val _selectedCategory = MutableStateFlow(1)

    val selectedCategory: StateFlow<Int> get() = _selectedCategory.asStateFlow()

    fun setSelectedCategory(categoryId: Int) {
        _selectedCategory.value = categoryId
    }
}