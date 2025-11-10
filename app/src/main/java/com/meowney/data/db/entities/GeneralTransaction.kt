package com.meowney.data.db.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
data class GeneralTransaction (
    @PrimaryKey (autoGenerate = true) val id: Int,
    @ColumnInfo (name = "title") var title: String,
    @ColumnInfo (name = "description") var description: String?,
    @ColumnInfo (name = "amount") var amount: Double,
    @ColumnInfo (name = "date") var date: String
)