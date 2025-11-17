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

    @Query("SELECT * FROM generaltransaction LIMIT 30")
    suspend fun getThirty(): List<GeneralTransaction>

    @Query("SELECT * FROM generaltransaction WHERE id = :id")
    suspend fun getById(id: Int): GeneralTransaction
}