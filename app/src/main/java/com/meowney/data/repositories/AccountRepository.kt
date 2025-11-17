package com.meowney.data.repositories

import com.meowney.data.database.dao.AccountDao
import com.meowney.data.database.entities.Account
import com.meowney.data.database.relations.AccountWithTransactions

class AccountRepository(private val dao: AccountDao) {

    suspend fun insertAccount(account: Account) {
        dao.insert(account)
    }

    suspend fun updateAccount(account: Account) {
        dao.update(account)
    }

    suspend fun deleteAccount(account: Account) {
        dao.delete(account)
    }

    suspend fun getAllAccounts(): List<Account> {
        return dao.getAll()
    }

    suspend fun getAccountById(id: Int): Account {
        return dao.getById(id)
    }

    suspend fun getAccountByName(name: String): Account {
        return dao.getByName(name)
    }

    suspend fun updateAccountBalance(id: Int, amount: Double) {
        dao.updateBalance(id, amount)
    }

    suspend fun getAccountWithTransactions(id: Int): AccountWithTransactions {
        return dao.getAccountWithTransactions(id)
    }
}