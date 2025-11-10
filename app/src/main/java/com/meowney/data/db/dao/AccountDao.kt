package com.meowney.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.meowney.data.db.entities.Account

@Dao
interface AccountDao {
    @Insert
    suspend fun insert(account: Account)

    @Update
    suspend fun update(account: Account)

    @Delete
    suspend fun delete(account: Account)

    @Query("SELECT * FROM account")
    suspend fun getAll(): List<Account>

    @Query("SELECT * FROM account WHERE id = :id")
    suspend fun getById(id: Int): Account

    @Query("SELECT * FROM account WHERE name = :name")
    suspend fun getByName(name: String): Account

    @Query("UPDATE account SET balance = balance + :amount WHERE id = :id")
    suspend fun updateBalance(id: Int, amount: Double)
}