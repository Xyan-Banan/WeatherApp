package ru.coddog.weatherapp.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.coddog.weatherapp.R
import ru.coddog.weatherapp.data.models.Forecast
import ru.coddog.weatherapp.databinding.ItemViewPagerBinding
import ru.coddog.weatherapp.domain.entities.DailyForecast
import ru.coddog.weatherapp.domain.entities.ForecastEntity

class ViewPagerAdapter() : RecyclerView.Adapter<ViewPagerAdapter.ViewPagerViewHolder>() {

    private val data = mutableListOf<DailyForecast>()

    fun setData(list: List<DailyForecast>) {
        data.clear()
        data.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_view_pager, parent, false)
        return ViewPagerViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewPagerViewHolder, position: Int) {
        val forecasts = data[position]
        holder.binding.forecastsRV
    }

    override fun getItemCount(): Int = data.size

    inner class ViewPagerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemViewPagerBinding.bind(itemView)
    }

}