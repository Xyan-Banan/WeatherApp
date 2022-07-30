package ru.coddog.weatherapp.presentation

import android.Manifest
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.edit
import androidx.core.location.LocationManagerCompat
import androidx.lifecycle.ViewModelProvider
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.tabs.TabLayoutMediator
import ru.coddog.weatherapp.R
import ru.coddog.weatherapp.WeatherApplication
import ru.coddog.weatherapp.databinding.ActivityMainBinding
import ru.coddog.weatherapp.domain.repositories.IRepository
import java.util.*
import javax.inject.Inject

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    @Inject
    lateinit var repository: IRepository

    private val binding: ActivityMainBinding by viewBinding(ActivityMainBinding::bind)

    private val locationPermissionRequest =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
            it.forEach { (k, v) -> Log.d(TAG, "permission $k granted: $v") }
//            Log.d(TAG, "permission granted: $it")
            updateLocation()
        }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: MainViewModel by viewModels { viewModelFactory }

    @Inject
    lateinit var locationManager: LocationManager

    @Inject
    lateinit var fusedLocationClient: FusedLocationProviderClient

    private lateinit var adapter: ViewPagerAdapter

    private lateinit var preferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val component = (application as WeatherApplication).component
        component.inject(this)

        preferences = getPreferences(MODE_PRIVATE)
        adapter = ViewPagerAdapter()
        binding.viewPager.adapter = adapter
    }

    override fun onStart() {
        super.onStart()

        getLocationFromPreferences()?.let {
            viewModel.loadForecasts(it)
            viewModel.updatePlace(it)
        } ?: run {
            Toast.makeText(this, "Нет сохраненных локаций", Toast.LENGTH_SHORT).show()
        }

        updateLocation()

        viewModel.dailyForecasts.observe(this) {
            adapter.setData(it)
            TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
                tab.text = DateMapper.positionToTabText(position, it)
            }.attach()
        }
        viewModel.isLoading.observe(this, binding.progressBar::setVisibility)
        viewModel.place.observe(this, binding.placeTV::setText)
    }

    private fun saveLocationToPreferences(location: Location) {
        preferences.edit() {
            this.putFloat(PREFERENCE_KEY_LATITUDE, location.latitude.toFloat())
            this.putFloat(PREFERENCE_KEY_LONGITUDE, location.longitude.toFloat())
        }
    }

    private fun getLocationFromPreferences(): Location? {
        if (!preferences.contains(PREFERENCE_KEY_LATITUDE)
            || !preferences.contains(PREFERENCE_KEY_LATITUDE)
        ) return null

        val latitude = preferences.getFloat(PREFERENCE_KEY_LATITUDE, 0f).toDouble()
        val longitude = preferences.getFloat(PREFERENCE_KEY_LONGITUDE, 0f).toDouble()

        return Location("").apply {
            this.latitude = latitude
            this.longitude = longitude
            Log.d(TAG, "getLocationFromPreferences: $this")
        }
    }

    private fun updateLocation() {
        if (!isLocationPermissionsGranted()) {
            Log.d(TAG, "getLocation: no permissions")
            requestLocationPermissions()
            return
        }

        if (!isLocationEnabled()) {
            Log.d(TAG, "getLocation: gps is not enabled")
            Toast.makeText(this, "Не удалось обновить геопозицию", Toast.LENGTH_SHORT).show()
            return
        }

        LocationManagerCompat.getCurrentLocation(
            locationManager,
            LocationManager.GPS_PROVIDER,
            null,
            mainExecutor
        ) {
//        fusedLocationClient.getCurrentLocation(Priority.PRIORITY_BALANCED_POWER_ACCURACY, null)
//            .addOnCompleteListener {
            Log.d(TAG, "getLocation1: $it")
//            Log.d(TAG, "getLocation1: ${it.result}")
//            Log.d(TAG, "getLocation1: ${it.isSuccessful}")
//            Log.d(TAG, "getLocation1: ${it.isCanceled}")
//            Log.d(TAG, "getLocation1: ${it.exception}")
            if (it == null)
                Toast.makeText(this, "Не удалось обновить геопозицию", Toast.LENGTH_SHORT)
                    .show()
            else {
                viewModel.loadForecasts(it)
                viewModel.updatePlace(it)
                saveLocationToPreferences(it)
            }
        }
    }

    private fun requestLocationPermissions() {
        locationPermissionRequest.launch(
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        )
    }

    private fun isLocationEnabled(): Boolean {
        return LocationManagerCompat.isLocationEnabled(locationManager)
    }

    private fun isLocationPermissionsGranted(): Boolean {
        return ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    companion object {
        private const val TAG = "MainActivity"
        private const val PREFERENCE_KEY_LATITUDE = "PREFERENCES_KEY_LATITUDE"
        private const val PREFERENCE_KEY_LONGITUDE = "PREFERENCES_KEY_LONGITUDE"
    }
}