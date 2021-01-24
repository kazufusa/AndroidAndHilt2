package com.example.myapplication.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface CountDao {
    @Query("SELECT count(*) FROM Count")
    suspend fun getCount(): Int

    @Query("SELECT * FROM Count where uid = 1 limit 1")
    suspend fun get(): Count

    @Insert
    suspend fun insert(count: Count)

    @Update
    suspend fun update(count: Count)
}