package com.example.cityweather.view

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ViewModelProvider
import com.example.cityweather.R
import com.example.cityweather.databinding.FragmentSplashBinding
import com.example.cityweather.databinding.FragmentWeatherBinding
import com.example.cityweather.model.DataClassWeather
import com.example.cityweather.model.DataClassWeatherData
import com.example.cityweather.model.DataClassWeatherInfo
import com.example.cityweather.utils.ScreenState
import com.example.cityweather.viewmodel.WeatherViewModel
import com.google.gson.Gson
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Fragment_Weather.newInstance] factory method to
 * create an instance of this fragment.
 */
class Fragment_Weather : Fragment(), LifecycleObserver {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    private lateinit var dataClassWeather: DataClassWeather


    private val viewModel : WeatherViewModel by lazy {
        ViewModelProvider(requireActivity())[WeatherViewModel::class.java]
    }


    //view binding
    private  var _bindingWeatherFragment: FragmentWeatherBinding?= null
    val getFragmentWeatherBinding get() = _bindingWeatherFragment!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _bindingWeatherFragment =  FragmentWeatherBinding.inflate(inflater, container, false)

        return  getFragmentWeatherBinding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Fragment_Weather.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Fragment_Weather().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        lifecycle.addObserver(this)

    }

    override fun onDetach() {
        super.onDetach()
        lifecycle.removeObserver(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onCreated(){

        viewModel.getLiveDataClassWeather.observe(viewLifecycleOwner) { weatherliveData ->

            if(weatherliveData == null)
                return@observe
            else
                populateWeatherData(weatherliveData)

            }



        }

    private fun populateWeatherData(weatherliveData: ScreenState<DataClassWeather?>) {

        when (weatherliveData) {
            is ScreenState.Loading -> {
                Toast.makeText(requireContext(),"Please wait..", Toast.LENGTH_SHORT).show()
            }
            is ScreenState.Success -> {
                if (weatherliveData.data != null) {

                    dataClassWeather = weatherliveData.data

                    val weatherList = dataClassWeather.data as ArrayList<DataClassWeatherData>

                    for (iitems in weatherList) {


                        val dataClassWeatherData: DataClassWeatherData = iitems
                        getFragmentWeatherBinding.constraintLayout.textViewWeatherTemp.text =
                            dataClassWeatherData.temp.toString()
                        val dataclassinfo: DataClassWeatherInfo = dataClassWeatherData.weather
                        getFragmentWeatherBinding.constraintLayout.textViewWeatherFeel.text =
                            "${dataclassinfo.description} ,  ${dataClassWeatherData.cityName} "


                    }
                }
                else {
                    Toast.makeText(requireContext(),"Failed to load weather info", Toast.LENGTH_SHORT).show()
                }
            }
            is ScreenState.Error -> {
                Toast.makeText(requireContext(),"Error ${weatherliveData.message}", Toast.LENGTH_SHORT).show()
            }

        }
    }
}
