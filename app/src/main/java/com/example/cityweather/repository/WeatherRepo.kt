package com.example.cityweather.repository

import com.example.cityweather.api.RetrofitSingleton

class WeatherRepo(  private val retrofitGateway: RetrofitSingleton) {
    fun getWeather(apikey: String, long: Double, lat: Double) = retrofitGateway.instance.getWeatherInfo(
        apikey,  long, lat
    )

}