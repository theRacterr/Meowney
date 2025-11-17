package com.meowney.data.repositories

import com.meowney.data.database.dao.GeneralTransactionDao
import com.meowney.data.database.entities.GeneralTransaction

class GeneralTransactionRepository(private val dao: GeneralTransactionDao) {

    suspend fun insertTransaction(transaction: GeneralTransaction) {
        dao.insert(transaction)
    }

    suspend fun updateTransaction(transaction: GeneralTransaction) {
        dao.update(transaction)
    }

    suspend fun deleteTransaction(transaction: GeneralTransaction) {
        dao.delete(transaction)
    }

    suspend fun getAllTransactions(): List<GeneralTransaction> {
        return dao.getAll()
    }

    suspend fun getTransactionById(id: Int): GeneralTransaction {
        return dao.getById(id)
    }

    suspend fun getThirtyTransactions(): List<GeneralTransaction> {
        return dao.getThirty()
    }
}