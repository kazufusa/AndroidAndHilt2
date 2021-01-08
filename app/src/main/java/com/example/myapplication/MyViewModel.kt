package com.example.myapplication

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.time.ZoneId

class MyViewModel @ViewModelInject constructor(
    private val service: ClockService
) : ViewModel() {
    var count: Int = 0
    val countLiveData = MutableLiveData<Int>(count)
    val clockLiveData = MutableLiveData<String>("")

    fun getCurrentTime() {
        viewModelScope.launch {
            val response = service.getClock()
            response?.body()?.st?.let {
                val t = java.time.Instant.ofEpochMilli((it*1000).toLong())
                    .atZone(ZoneId.of("Asia/Tokyo"))
                clockLiveData.postValue(t.toString())
            }
        }
    }

    fun increment() {
        countLiveData.postValue(++count)
    }

    fun decrement() {
        countLiveData.postValue(--count)
    }
}