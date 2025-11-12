package com.meowney.data.db.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.meowney.data.db.entities.GeneralTransaction
import com.meowney.data.db.entities.TransactionCategory

data class CategoryWithTransactions(
    @Embedded val category: TransactionCategory,
    @Relation(
        parentColumn = "id",
        entityColumn = "category_id"
    )
    val generalTransactions: List<GeneralTransaction>
)