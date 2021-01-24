package com.example.myapplication.vm

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.db.CountDao
import com.example.myapplication.repo.ApiRepository
import com.example.myapplication.repo.CountRepository
import kotlinx.coroutines.launch

class MyViewModel @ViewModelInject constructor(
    private val apiRepo: ApiRepository,
    private val countRepo: CountRepository
) : ViewModel() {
    var count: Int = 0
    val countLiveData = MutableLiveData<Int>(count)

    init {
        // https://developer.android.com/codelabs/android-room-with-a-view-kotlin?hl=ja#8
        viewModelScope.launch {
            Log.i("VIEWMODEL", "init")
            Log.i("VIEWMODEL", countRepo.get().toString())
        }
    }

    fun getCurrentTime() = apiRepo.getClock()

    fun increment() {
        countLiveData.postValue(++count)
    }

    fun decrement() {
        countLiveData.postValue(--count)
    }
}
