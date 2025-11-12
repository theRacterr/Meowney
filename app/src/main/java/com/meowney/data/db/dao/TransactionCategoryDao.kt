package com.meowney.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.meowney.data.db.entities.TransactionCategory
import com.meowney.data.db.relations.CategoryWithTransactions

@Dao
interface TransactionCategoryDao {
    @Insert
    suspend fun insert(category: TransactionCategory)

    @Update
    suspend fun update(category: TransactionCategory)

    @Delete
    suspend fun delete(category: TransactionCategory)

    @Query("SELECT * FROM transactioncategory")
    suspend fun getAll(): List<TransactionCategory>

    @Query("SELECT * FROM transactioncategory WHERE name = :name")
    suspend fun getByName(name: String): TransactionCategory?

    @Transaction
    @Query("SELECT * FROM transactioncategory WHERE id = :id")
    suspend fun getCategoryWithTransactions(id: Int): CategoryWithTransactions
}