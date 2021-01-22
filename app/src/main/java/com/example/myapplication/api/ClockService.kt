package com.example.myapplication.api

import retrofit2.http.GET

interface ClockService {
    @GET("UnixTime/tounixtimestamp?datetime=now")
    suspend fun getClock(): Clock
}

data class Clock(
    val UnixTimeStamp: Double
)
