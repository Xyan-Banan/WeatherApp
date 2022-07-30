package ru.coddog.weatherapp.presentation

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import ru.coddog.weatherapp.R
import java.util.*

fun String.capitalize() =
    replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }

fun ImageView.loadImage(url: String, progressBar: ProgressBar? = null) {
    Glide.with(this)
        .load(url)
        .error(R.drawable.ic_broken_image_24)
        .into(object : CustomTarget<Drawable>() {
            override fun onResourceReady(
                resource: Drawable,
                transition: Transition<in Drawable>?
            ) {
                this@loadImage.setImageDrawable(resource)
                progressBar?.isVisible = false
            }

            override fun onLoadCleared(placeholder: Drawable?) {
                this@loadImage.setImageDrawable(placeholder)
                progressBar?.isVisible = false
            }
        })
}

fun View.setVisibility(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}