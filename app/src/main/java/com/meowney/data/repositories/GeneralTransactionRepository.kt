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

    suspend fun getSumOfAll(): Double? {
        return dao.getSumOfAll()
    }

    suspend fun getMonthlySum(): List<GeneralTransactionDao.MonthlySum> {
        return dao.getMonthlySum()
    }

    suspend fun getMonthlySumByAccount(accountId: Int): List<GeneralTransactionDao.MonthlySum> {
        return dao.getMonthlySumByAccount(accountId)
    }

    suspend fun getCategorySums(): List<GeneralTransactionDao.CategorySum> {
        return dao.getCategorySums()
    }

    suspend fun getCategorySumsByAccount(accountId: Int): List<GeneralTransactionDao.CategorySum> {
        return dao.getCategorySumsByAccount(accountId)
    }

    suspend fun getTransactionById(id: Int): GeneralTransaction {
        return dao.getById(id)
    }

    suspend fun getAllTransactionsByAccountId(accountId: Int): List<GeneralTransaction> {
        return dao.getAllByAccountId(accountId)
    }

    suspend fun getSumByAccountId(accountId: Int): Double? {
        return dao.getSumByAccountId(accountId)
    }
}