package com.example.myapplication.repo

import androidx.annotation.WorkerThread
import com.example.myapplication.db.Count
import com.example.myapplication.db.CountDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CountRepository @Inject constructor(
    private val dao: CountDao
) {
    suspend fun get(): Int {
        if (dao.getCount() == 0) {
            dao.insert(Count(uid = 1, count = 0))
        }
        return dao.get().count
    }

    suspend fun increment(): Int {
        if (dao.getCount() == 0) {
            dao.insert(Count(uid = 1, count = 0))
        }
        val count = dao.get()
        dao.update(Count(uid=1, count=count.count+1))
        return count.count+1
    }

    suspend fun decrement(): Int {
        if (dao.getCount() == 0) {
            dao.insert(Count(uid = 1, count = 0))
        }
        val count = dao.get()
        dao.update(Count(uid=1, count=count.count-1))
        return count.count-1
    }
}