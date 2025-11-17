package com.meowney.data.repositories

import com.meowney.data.database.dao.TransactionCategoryDao
import com.meowney.data.database.entities.TransactionCategory
import com.meowney.data.database.relations.CategoryWithTransactions

class TransactionCategoryRepository(private val dao: TransactionCategoryDao) {
    suspend fun insertCategory(category: TransactionCategory) {
        dao.insert(category)
    }

    suspend fun updateCategory(category: TransactionCategory) {
        dao.update(category)
    }

    suspend fun deleteCategory(category: TransactionCategory) {
        dao.delete(category)
    }

    suspend fun getAllCategories(): List<TransactionCategory> {
        return dao.getAll()
    }

    suspend fun getCategoryByName(name: String): TransactionCategory? {
        return dao.getByName(name)
    }

    suspend fun getCategoryWithTransactions(id: Int): CategoryWithTransactions {
        return dao.getCategoryWithTransactions(id)
    }

}
