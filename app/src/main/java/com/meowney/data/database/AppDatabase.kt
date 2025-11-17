package com.meowney.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.meowney.data.database.dao.AccountDao
import com.meowney.data.database.dao.GeneralTransactionDao
import com.meowney.data.database.dao.TransactionCategoryDao
import com.meowney.data.database.entities.Account
import com.meowney.data.database.entities.GeneralTransaction
import com.meowney.data.database.entities.TransactionCategory

@Database(entities = [Account::class, GeneralTransaction::class, TransactionCategory::class], version=1, exportSchema = false) // schema export disabled till app version v1.0.0
abstract class AppDatabase : RoomDatabase() {
    abstract fun accountDao(): AccountDao
    abstract fun generalTransactionDao(): GeneralTransactionDao

    abstract fun transactionCategoryDao(): TransactionCategoryDao
}