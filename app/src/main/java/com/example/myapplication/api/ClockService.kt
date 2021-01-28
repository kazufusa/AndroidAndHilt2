package com.example.myapplication.api

import okhttp3.MultipartBody
import retrofit2.http.*

interface ClockService {
    @GET("unixtime")
    suspend fun getClock(@Header("Token") token: String): Clock

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
