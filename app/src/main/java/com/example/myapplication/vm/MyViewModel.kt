package com.example.myapplication.vm

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.repo.ApiRepository
import com.example.myapplication.repo.CountRepository
import kotlinx.coroutines.launch
import java.io.File

class MyViewModel @ViewModelInject constructor(
    private val apiRepo: ApiRepository,
    private val countRepo: CountRepository
) : ViewModel() {
    val countLiveData = MutableLiveData<Int>()

    init {
        viewModelScope.launch {
            countLiveData.postValue(countRepo.get())
        }
    }

    fun getCurrentTime() = apiRepo.getClock()

    fun postPhoto(file: File) = apiRepo.postImage(file)

    fun increment() {
        viewModelScope.launch {
            countLiveData.postValue(countRepo.increment())
        }
    }

    fun decrement() {
        viewModelScope.launch {
            countLiveData.postValue(countRepo.decrement())
        }
    }
}
