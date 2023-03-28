package com.example.cityweather

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.cityweather.databinding.ActivityMainBinding
import com.example.cityweather.view.Fragment_Splash
import com.example.cityweather.viewmodel.WeatherViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import java.util.*
import kotlin.concurrent.schedule

class MainActivity : AppCompatActivity() {
    private val ACCESS_FINE_LOCATION_PERMISSION = 1
    private lateinit var bindingMainActivity: ActivityMainBinding

    //view model repo
    private val viewModel: WeatherViewModel by lazy {
        ViewModelProvider(this)[WeatherViewModel::class.java]
    }


    private val fusedLocationClient by lazy {
        FusedLocationProviderClient(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingMainActivity = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindingMainActivity.root)


        Check_Permmission()
    }

    fun getLocation() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            Check_Permmission();
            return
        }
        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                println("Location= " + location.latitude)
                val latitdue = location.latitude
                val longitude = location.longitude

                viewModel.getWeatherDetails(longitude, latitdue)
            }
        }
    }


    fun Check_Permmission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            getLocation();

            RunThread()

        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.CAMERA
                )
            ) {
                AlertDialog.Builder(this)
                    .setTitle("Permission needed")
                    .setMessage("Location permission is needed")
                    .setPositiveButton(
                        "Ok"
                    ) { dialogInterface, i ->
                        ActivityCompat.requestPermissions(
                            this@MainActivity,
                            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                            ACCESS_FINE_LOCATION_PERMISSION
                        )
                    }
                    .create().show()
            } else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    ACCESS_FINE_LOCATION_PERMISSION
                )
            }
        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == ACCESS_FINE_LOCATION_PERMISSION) {
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(applicationContext, "Permission Granted", Toast.LENGTH_SHORT).show()
                RunThread()
                getLocation();
            } else {
                finish()
                System.exit(0)
                Toast.makeText(applicationContext, "Permission denied", Toast.LENGTH_SHORT).show()
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.CAMERA),
                    ACCESS_FINE_LOCATION_PERMISSION
                )
            }
        }
    }

    private fun RunThread() {
        Timer().schedule(2500) {
            supportFragmentManager.beginTransaction().replace(
                R.id.fragment_container, Fragment_Splash()
            ).commit()
        }

    }


}