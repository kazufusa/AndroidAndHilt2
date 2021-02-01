package com.example.myapplication.api

import okhttp3.MultipartBody
import okhttp3.Request
import okhttp3.RequestBody
import retrofit2.http.*

interface ClockService {
    @GET("unixtime")
    suspend fun getClock(@Header("Token") token: String): Clock

    @POST("upload")
    @Multipart
    suspend fun postPhoto(
        @Part("name") name: RequestBody,
        @Part imageData: MultipartBody.Part
    ): UploadResponse

    @POST("upload")
    @Multipart
    suspend fun postPhoto2(
        @PartMap params: MutableMap<String, RequestBody>
    ): UploadResponse
}

data class Clock(
    val UnixTimeStamp: Double
)

data class UploadResponse(
    val success: Boolean
)
