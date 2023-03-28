package com.example.cityweather.view

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.cityweather.R
import com.example.cityweather.databinding.FragmentSplashBinding
import com.example.cityweather.viewmodel.WeatherViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import java.util.*
import kotlin.concurrent.schedule

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Fragment_Splash.newInstance] factory method to
 * create an instance of this fragment.
 */
class Fragment_Splash : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null


    //view model repo
    private val viewModel : WeatherViewModel by lazy {
        ViewModelProvider(requireActivity())[WeatherViewModel::class.java]
    }


    //view binding
    private  var _bindingSplashFragment: FragmentSplashBinding ?= null
    val getBindingSplashFragment get() = _bindingSplashFragment!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _bindingSplashFragment = FragmentSplashBinding.inflate(inflater, container, false)

        return  getBindingSplashFragment.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        movetoLocation()

    }


    fun movetoLocation(){
        Timer().schedule(2500) {
            requireActivity().supportFragmentManager.beginTransaction().replace(
                R.id.fragment_container,
                Fragment_Weather()
            ).commit()
        }



    }



    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Fragment_Splash.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Fragment_Splash().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }


}