package com.meowney.data.db.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.meowney.data.db.entities.Account
import com.meowney.data.db.entities.GeneralTransaction

data class AccountWithTransactions (
    @Embedded val account: Account,
    @Relation(
        parentColumn = "id",
        entityColumn = "account_id"
    )
    val generalTransactions: List<GeneralTransaction>
)