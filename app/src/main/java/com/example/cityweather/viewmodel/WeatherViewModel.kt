package com.example.cityweather.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.cityweather.api.RetrofitSingleton
import com.example.cityweather.model.DataClassWeather
import com.example.cityweather.repository.WeatherRepo
import com.example.cityweather.utils.ScreenState
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.awaitResponse

class WeatherViewModel(application: Application): AndroidViewModel(application ) {

    private lateinit var weatherRepo: WeatherRepo
    companion object{
      const  val authkey="2d1fac9de9mshff1720160e522ddp1916ecjsndd91adcb2d91"
    }

    init {
        weatherRepo =WeatherRepo(RetrofitSingleton)
    }
    private
     var _dataClassWeatherLiveData: MutableLiveData<ScreenState<DataClassWeather?>> =
        MutableLiveData()

    val getLiveDataClassWeather : LiveData<ScreenState<DataClassWeather?>> get() = _dataClassWeatherLiveData


    fun getWeatherDetails(long:Double, lat:Double) {
        _dataClassWeatherLiveData.postValue(ScreenState.Loading(null))


        val coroutineExceptionHandler = CoroutineExceptionHandler { context, throwable ->
            throwable.printStackTrace()
            _dataClassWeatherLiveData.postValue(ScreenState.Error(throwable.message.toString(), null))
        }



        viewModelScope.launch(coroutineExceptionHandler + Dispatchers.IO) {
            val callDoctorProfileResponse = weatherRepo.getWeather(
                authkey,  long,  lat
            ).awaitResponse()

            if (callDoctorProfileResponse.isSuccessful) {
                val dataClassWeather: DataClassWeather = Gson().fromJson(callDoctorProfileResponse.body()?.string(), DataClassWeather::class.java)
                _dataClassWeatherLiveData.postValue(ScreenState.Success(dataClassWeather))
            }
            else {
                _dataClassWeatherLiveData.postValue(
                    ScreenState.Error(
                        callDoctorProfileResponse.code().toString(),
                        null
                    )
                )

            }
        }
    }

}