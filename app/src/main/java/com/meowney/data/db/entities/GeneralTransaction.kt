package com.meowney.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = Account::class,
            parentColumns = ["id"],
            childColumns = ["account_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = TransactionCategory::class,
            parentColumns = ["id"],
            childColumns = ["category_id"]
        )
    ]
)
data class GeneralTransaction (
    @PrimaryKey (autoGenerate = true) val id: Int,
    @ColumnInfo (name = "account_id", index = true) var accountId: Int,
    @ColumnInfo (name = "category_id", index = true) var categoryId: Int?,
    @ColumnInfo (name = "title") var title: String,
    @ColumnInfo (name = "description") var description: String?,
    @ColumnInfo (name = "amount") var amount: Double,
    @ColumnInfo (name = "date") var date: String?
)