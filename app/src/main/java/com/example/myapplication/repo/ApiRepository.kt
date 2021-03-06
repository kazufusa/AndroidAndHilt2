package com.example.myapplication.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.myapplication.api.ClockService
import com.example.myapplication.util.Resource
import kotlinx.coroutines.Dispatchers
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
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
            service.getClock("TEST_TOKEN").UnixTimeStamp.let {
                java.time.Instant.ofEpochMilli((it * 1000).toLong())
                    .atZone(ZoneId.of("Asia/Tokyo"))
            }
        }
    }

    fun postImage(file: File): LiveData<Resource<Boolean>> {
        return execute {
            service.postPhoto(
                file.name.toRequestBody(),
                MultipartBody.Part.createFormData(
                    "file", file.name,
                    file.asRequestBody(file.extension.toMediaTypeOrNull())
                )
            ).let {
                it.success
            }
        }
    }

    fun postImage2(file: File): LiveData<Resource<Boolean>> {
        return execute {
            service.postPhoto2(
                mutableMapOf(
                    "name" to file.name.toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull()),
                    "file\"; filename=\"" + file.name to file.asRequestBody(file.extension.toMediaTypeOrNull())
                )
            ).let {
                it.success
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
