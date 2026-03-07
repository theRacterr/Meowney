package com.meowney.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.meowney.data.database.entities.GeneralTransaction

@Dao
interface GeneralTransactionDao {

    @Insert
    suspend fun insert(entry: GeneralTransaction)

    @Update
    suspend fun update(entry: GeneralTransaction)

    @Delete
    suspend fun delete(entry: GeneralTransaction)

    @Query("SELECT * FROM generaltransaction")
    suspend fun getAll(): List<GeneralTransaction>

    @Query("SELECT SUM(amount) FROM generaltransaction")
    suspend fun getSumOfAll(): Double?

    @Query("SELECT * FROM generaltransaction WHERE id = :id")
    suspend fun getById(id: Int): GeneralTransaction

    @Query("SELECT * FROM generaltransaction WHERE account_id = :accountId")
    suspend fun getAllByAccountId(accountId: Int): List<GeneralTransaction>

    @Query("SELECT SUM(amount) FROM GeneralTransaction WHERE account_id = :accountId")
    suspend fun getSumByAccountId(accountId: Int): Double?
}