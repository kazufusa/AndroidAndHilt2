package com.example.myapplication

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MyViewModel : ViewModel() {
    var count: Int = 0
    val countLiveData = MutableLiveData<Int>(count)

    fun increment() {
        countLiveData.postValue(++count)
    }

    fun decrement() {
        countLiveData.postValue(--count)
    }
}