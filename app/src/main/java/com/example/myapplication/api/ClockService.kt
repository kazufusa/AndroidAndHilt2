package com.example.myapplication.api

import okhttp3.MultipartBody
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ClockService {
    @GET("unixtime")
    suspend fun getClock(): Clock

    @POST("upload")
    @Multipart
    suspend fun postPhoto(
        @Part("name") name: String,
        @Part() imageData: MultipartBody.Part
    ): UploadResponse
}

data class Clock(
    val UnixTimeStamp: Double
)

data class UploadResponse(
    val success: Boolean
)
