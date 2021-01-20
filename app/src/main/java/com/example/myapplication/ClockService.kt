package com.example.myapplication

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface ClockService {
    @GET("cgi-bin/json")
    suspend fun getClock(): Response<ClockResponse>
}