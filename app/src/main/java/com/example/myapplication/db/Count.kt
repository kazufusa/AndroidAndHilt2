package com.example.myapplication.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Count(
    @PrimaryKey(autoGenerate = true) val uid: Int,
    @ColumnInfo(name = "count") val count: Int
)
