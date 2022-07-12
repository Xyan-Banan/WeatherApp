package ru.coddog.weatherapp.presentation

import android.util.Log
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
        Log.d(TAG, "onBindViewHolder: called")
        holder.binding.forecastTV.text = data[position].description
    }

    override fun getItemCount(): Int = data.size

    companion object {
        private const val TAG = "DailyForecastRvAdapter"
    }
}