package com.meowney.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.meowney.data.db.entities.TransactionCategory

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
}