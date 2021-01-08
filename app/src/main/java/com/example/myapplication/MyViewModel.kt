package com.example.myapplication

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MyViewModel @ViewModelInject constructor(): ViewModel() {
    var count: Int = 0
    val countLiveData = MutableLiveData<Int>(count)

    fun increment() {
        countLiveData.postValue(++count)
    }

    fun decrement() {
        countLiveData.postValue(--count)
    }
}