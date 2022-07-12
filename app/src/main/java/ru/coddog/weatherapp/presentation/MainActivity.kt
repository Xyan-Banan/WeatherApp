package ru.coddog.weatherapp.presentation

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.coddog.weatherapp.R
import ru.coddog.weatherapp.WeatherApplication
import ru.coddog.weatherapp.databinding.ActivityMainBinding
import ru.coddog.weatherapp.domain.repositories.IRepository
import javax.inject.Inject

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    @Inject
    lateinit var repository: IRepository

    private val binding: ActivityMainBinding by viewBinding(ActivityMainBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val component = (application as WeatherApplication).component
        component.inject(this)

        val adapter = DailyForecastRvAdapter()
        binding.forecastsRV.adapter = adapter
        binding.forecastsRV.layoutManager = LinearLayoutManager(this)

        repository.getForecastByCity("Saint Petersburg")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d(TAG, "onCreate: ${it.joinToString("\n")}")
                adapter.setData(it[0].list)
            }) {
                Log.e(TAG, "onCreate: ${it.message}")
            }
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}