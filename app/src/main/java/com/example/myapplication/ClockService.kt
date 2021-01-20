package com.example.myapplication

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface ClockService {
    @GET("UnixTime/tounixtimestamp?datetime=now")
    suspend fun getClock(): Response<ClockResponse>
}
