package com.example.myapplication.api

import com.example.myapplication.vo.Clock
import retrofit2.Response
import retrofit2.http.GET

interface ClockService {
    @GET("UnixTime/tounixtimestamp?datetime=now")
    suspend fun getClock(): Response<Clock>
}
