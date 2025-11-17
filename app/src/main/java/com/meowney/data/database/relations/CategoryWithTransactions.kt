package com.meowney.data.database.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.meowney.data.database.entities.GeneralTransaction
import com.meowney.data.database.entities.TransactionCategory

data class CategoryWithTransactions(
    @Embedded val category: TransactionCategory,
    @Relation(
        parentColumn = "id",
        entityColumn = "category_id"
    )
    val generalTransactions: List<GeneralTransaction>
)