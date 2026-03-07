package com.meowney.ui.entries

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class EntriesViewModel : ViewModel() {

    private val _selectedAccount = MutableStateFlow(0)
    val selectedAccount: StateFlow<Int> get() = _selectedAccount.asStateFlow()

    fun setSelectedAccount(accountId: Int) {
        _selectedAccount.value = accountId
    }

}