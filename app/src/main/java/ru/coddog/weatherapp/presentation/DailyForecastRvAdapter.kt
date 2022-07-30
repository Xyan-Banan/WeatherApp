package ru.coddog.weatherapp.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.coddog.weatherapp.R
import ru.coddog.weatherapp.databinding.ItemVerticalRecyclerViewBinding
import ru.coddog.weatherapp.domain.entities.ForecastEntity

class DailyForecastRvAdapter : RecyclerView.Adapter<DailyForecastRvAdapter.ForecastViewHolder>() {

    private val data = mutableListOf<ForecastEntity>()

    fun setData(list: List<ForecastEntity>) {
        data.clear()
        data.addAll(list)
        notifyDataSetChanged()
    }

    inner class ForecastViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemVerticalRecyclerViewBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_vertical_recycler_view, parent, false)
        return ForecastViewHolder(view)
    }

    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {
        val forecast = data[position]
        with(holder.binding)
        {
            timeTV.text =
                forecast.dateTime.toString() + " - " + forecast.dateTime.plusHours(3).toString()
            descriptionTV.text = forecast.description.capitalize()
            iconIV.loadImage(forecast.iconUrl, progressBar)
            tempAvgTV.text = forecast.temp.avg.toString() + DEGREES
            tempFeelsLikeTV.text = forecast.temp.feelsLike.toString() + DEGREES
            tempMaxTV.text = forecast.temp.max.toString() + DEGREES
            tempMinTV.text = forecast.temp.min.toString() + DEGREES
            humidityTV.text = forecast.humidity.toString() + PERCENTS
            windSpeedTV.text = forecast.wind.speed.toString() + METERS_PER_SECOND
            pressureTV.text = forecast.pressure.current.toString() + MMHG
        }
    }

    override fun getItemCount(): Int = data.size

    companion object {
        private const val TAG = "DailyForecastRvAdapter"
        private const val DEGREES = "°C"
        private const val PERCENTS = "%"
        private const val METERS_PER_SECOND = "м/с"
        private const val MMHG = "мм рт.ст."
    }
}

