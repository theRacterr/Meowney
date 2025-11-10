package com.meowney.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.meowney.data.db.dao.AccountDao
import com.meowney.data.db.entities.Account

@Database(entities = [Account::class], version=1, exportSchema = false) // schema export disabled till app version v1.0.0
abstract class AppDatabase : RoomDatabase() {
    abstract fun accountDao(): AccountDao
}