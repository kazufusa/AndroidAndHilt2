package com.example.myapplication.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.myapplication.api.ClockService
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

enum class Status {
    SUCCESS,
    ERROR,
    LOADING,
    UNKNOWN
}

class Resource<out T> private constructor(val status: Status, val data: T?, val message: String?) {
    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(Status.SUCCESS, data, null)
        }

        fun <T> error(msg: String?, data: T? = null): Resource<T> {
            return Resource(Status.ERROR, data, msg)
        }

        fun <T> loading(): Resource<T> {
            return Resource(Status.LOADING, null, null)
        }
    }
}

fun <T> handleResource(
    res: Resource<T>,
    onLoading: (data: T?) -> Unit = { _ -> },
    onSuccess: (data: T?) -> Unit = { _ -> },
    onError: (message: String?, data: T?) -> Unit = { _, _ -> }
) {
    when (res.status) {
        Status.LOADING -> onLoading(res.data)
        Status.SUCCESS -> onSuccess(res.data)
        Status.ERROR -> onError(res.message, res.data)
        else -> {}
    }
}
