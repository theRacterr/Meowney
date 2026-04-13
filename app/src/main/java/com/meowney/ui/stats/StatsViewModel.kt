package com.meowney.ui.stats

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class StatsViewModel : ViewModel() {

    private val _selectedAccount = MutableStateFlow(0)

    val selectedAccount: StateFlow<Int> get() = _selectedAccount.asStateFlow()

    fun setSelectedAccount(accountId: Int) {
        _selectedAccount.value = accountId
    }

}