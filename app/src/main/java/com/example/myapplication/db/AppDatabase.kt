package com.example.myapplication.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(Count::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun countDao(): CountDao
}