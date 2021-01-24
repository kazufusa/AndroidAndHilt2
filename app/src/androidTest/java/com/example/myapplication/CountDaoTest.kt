package com.example.myapplication

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ActivityScenario.launch
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.myapplication.db.AppDatabase
import com.example.myapplication.db.Count
import com.example.myapplication.db.CountDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.hamcrest.CoreMatchers.equalTo
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class CountReadWriteTest {
    private lateinit var countDao: CountDao
    private lateinit var db: AppDatabase
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java
        ).build()
        countDao = db.countDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun getCount() = runBlocking {
        assertThat(countDao.getCount(), equalTo(0))
        countDao.insert(Count(uid = 1, count = 2))
        assertThat(countDao.getCount(), equalTo(1))
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGet() = runBlocking {
        countDao.insert(Count(uid = 1, count = 1))
        assertThat(countDao.getCount(), equalTo(1))
        var count = countDao.get()
        assertThat(count.uid, equalTo(1))
        assertThat(count.count, equalTo(1))
    }

    @Test
    @Throws(Exception::class)
    fun update() = runBlocking {
        countDao.insert(Count(uid = 1, count = 1))
        countDao.update(Count(uid = 1, count = 3))
        val count = countDao.get()
        assertThat(countDao.getCount(), equalTo(1))
        assertThat(count.uid, equalTo(1))
        assertThat(count.count, equalTo(3))
    }
}