package com.example.myapplication.vm

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.api.ClockService
import com.example.myapplication.repo.ApiRepository
import kotlinx.coroutines.launch
import java.time.ZoneId

class MyViewModel @ViewModelInject constructor(
    private val repository: ApiRepository
) : ViewModel() {
    var count: Int = 0
    val countLiveData = MutableLiveData<Int>(count)

    fun getCurrentTime() = repository.getClock()

    fun increment() {
        countLiveData.postValue(++count)
    }

    fun decrement() {
        countLiveData.postValue(--count)
    }
}
