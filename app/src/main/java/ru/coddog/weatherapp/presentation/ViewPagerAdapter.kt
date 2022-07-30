package ru.coddog.weatherapp.presentation

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.coddog.weatherapp.R
import ru.coddog.weatherapp.databinding.ItemViewPagerBinding
import ru.coddog.weatherapp.domain.entities.DailyForecast

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
        return ViewPagerViewHolder(view, parent.context)
    }

    override fun onBindViewHolder(holder: ViewPagerViewHolder, position: Int) {
        val dailyForecast = data[position]
        holder.adapter.setData(dailyForecast.list)
    }

    override fun getItemCount(): Int = data.size

    inner class ViewPagerViewHolder(itemView: View, context: Context) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemViewPagerBinding.bind(itemView)
        val adapter = DailyForecastRvAdapter()

        init {
            binding.forecastsRV.adapter = adapter
            binding.forecastsRV.layoutManager = LinearLayoutManager(context)
        }
    }

}