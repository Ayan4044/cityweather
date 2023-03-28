package com.example.cityweather.api

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query

interface API {

    @Headers(
        "x-rapidapi-host:weatherbit-v1-mashape.p.rapidapi.com",
      //  "x-rapidapi-key:2d1fac9de9mshff1720160e522ddp1916ecjsndd91adcb2d91"
    )
    @GET("current")
    fun getWeatherInfo(
        @Header("x-rapidapi-key") apikey:String,
        @Query("lon") long: Double,
        @Query("lat") lat: Double
    ): Call<ResponseBody>
}