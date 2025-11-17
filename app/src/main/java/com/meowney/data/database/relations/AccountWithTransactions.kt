package com.meowney.data.database.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.meowney.data.database.entities.Account
import com.meowney.data.database.entities.GeneralTransaction

data class AccountWithTransactions (
    @Embedded val account: Account,
    @Relation(
        parentColumn = "id",
        entityColumn = "account_id"
    )
    val generalTransactions: List<GeneralTransaction>
)