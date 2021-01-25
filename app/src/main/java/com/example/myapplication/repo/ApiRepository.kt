package com.example.myapplication.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.myapplication.api.ClockService
import com.example.myapplication.util.Resource
import kotlinx.coroutines.Dispatchers
import java.time.ZoneId
import java.time.ZonedDateTime
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiRepository @Inject constructor(
    private val service: ClockService
) {
    fun getClock(): LiveData<Resource<ZonedDateTime>> {
        return execute {
            service.getClock().UnixTimeStamp.let {
                java.time.Instant.ofEpochMilli((it * 1000).toLong())
                    .atZone(ZoneId.of("Asia/Tokyo"))
            }
        }
    }

    private fun <T> execute(function: suspend () -> T): LiveData<Resource<T>> {
        return liveData(Dispatchers.IO) {
            emit(Resource.loading())
            try {
                emit(Resource.success(function()))
            } catch (exception: Exception) {
                emit(Resource.error(exception.message))
            }
        }
    }
}
