package com.meowney

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    // user created accounts start with id = 1
    // id = 0 is reserved for accessing data from all accounts at once

    private val _selectedAccount = MutableLiveData<Int>(0)
    val selectedAccount: LiveData<Int> get() = _selectedAccount

    fun selectAccount(accountId: Int) {
        _selectedAccount.value = accountId
    }
}