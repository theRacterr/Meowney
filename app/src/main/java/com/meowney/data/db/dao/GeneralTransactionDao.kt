package com.meowney.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.meowney.data.db.entities.GeneralTransaction

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

    // TODO: further queries
}