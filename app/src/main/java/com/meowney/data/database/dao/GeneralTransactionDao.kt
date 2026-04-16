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

    @Query("""
        SELECT 
            strftime('%Y-%m', date) AS month, 
            SUM(amount) AS totalSum
        FROM GeneralTransaction
        GROUP BY month
        ORDER BY month ASC;
    """)
    suspend fun getMonthlySum(): List<MonthlySum>

    @Query("""
        SELECT 
            strftime('%Y-%m', date) AS month, 
            SUM(amount) AS totalSum
        FROM GeneralTransaction
        WHERE account_id = :accountId
        GROUP BY month
        ORDER BY month ASC;
    """)
    suspend fun getMonthlySumByAccount(accountId: Int): List<MonthlySum>

    @Query("SELECT * FROM generaltransaction WHERE id = :id")
    suspend fun getById(id: Int): GeneralTransaction

    @Query("SELECT * FROM generaltransaction WHERE account_id = :accountId")
    suspend fun getAllByAccountId(accountId: Int): List<GeneralTransaction>

    @Query("SELECT SUM(amount) FROM GeneralTransaction WHERE account_id = :accountId")
    suspend fun getSumByAccountId(accountId: Int): Double?

    data class MonthlySum(
        val month: String,
        val totalSum: Double
    )

}