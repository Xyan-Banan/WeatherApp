package ru.coddog.weatherapp.presentation

import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import ru.coddog.weatherapp.R
import ru.coddog.weatherapp.databinding.ItemVerticalRecyclerViewBinding
import ru.coddog.weatherapp.domain.entities.ForecastEntity
import java.util.*

class DailyForecastRvAdapter : RecyclerView.Adapter<DailyForecastRvAdapter.ForecastViewHolder>() {

    private val data = mutableListOf<ForecastEntity>()

    fun setData(list: List<ForecastEntity>) {
        data.clear()
        data.addAll(list)
        notifyDataSetChanged()
        Log.d(TAG, "setData: called")
    }

    inner class ForecastViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemVerticalRecyclerViewBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_vertical_recycler_view, parent, false)
        Log.d(TAG, "onCreateViewHolder: called")
        return ForecastViewHolder(view)
    }

    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {
        val forecast = data[position]
        Log.d(TAG, "onBindViewHolder: called ${forecast.description} ${forecast.dateTime}")
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

    fun String.capitalize() =
        replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }

    private fun ImageView.loadImage(url: String, progressBar: ProgressBar) {
        Glide.with(this)
            .load(url)
            .error(R.drawable.ic_broken_image_24)
            .into(object : CustomTarget<Drawable>() {
                override fun onResourceReady(
                    resource: Drawable,
                    transition: Transition<in Drawable>?
                ) {
                    this@loadImage.setImageDrawable(resource)
                    progressBar.isVisible = false
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                    this@loadImage.setImageDrawable(placeholder)
                    progressBar.isVisible = false
                }
            })
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

