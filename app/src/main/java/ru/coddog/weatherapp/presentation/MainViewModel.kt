package ru.coddog.weatherapp.presentation

import android.location.Geocoder
import android.location.Location
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import ru.coddog.weatherapp.domain.entities.DailyForecast
import ru.coddog.weatherapp.domain.repositories.IRepository
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val repository: IRepository,
    private val geocoder: Geocoder
) : ViewModel() {
    private val disposable = CompositeDisposable()

    private val _dailyForecasts = MutableLiveData<List<DailyForecast>>()
    val dailyForecasts: LiveData<List<DailyForecast>> get() = _dailyForecasts

    private val _place = MutableLiveData("...")
    val place: LiveData<String> get() = _place

    private val _isLoading = MutableLiveData(true)
    val isLoading: LiveData<Boolean> get() = _isLoading

    fun loadForecasts(location: Location) {
        repository.getForecastByCoordinates(location.latitude, location.longitude)
            .async()
            .doOnSubscribe { _isLoading.value = true }
            .doOnEvent { _, _ -> _isLoading.value = false }
            .subscribeBy {
                _dailyForecasts.value = it
            }
            .addTo(disposable)
    }

    fun updatePlace(location: Location) {
        Single.just(geocoder.getFromLocation(location.latitude, location.longitude, 1).first())
            .async()
            .subscribeBy(onSuccess = {
                _place.value = it.locality ?: it.subAdminArea ?: it.adminArea
                Log.d(TAG, "updatePlace: $it")
            }, onError = {
                _place.value = "Не удалось получить название"
            }).addTo(disposable)
            .addToComposite()
    }

    private fun <T> Single<T>.async(scheduler: Scheduler = Schedulers.io()) =
        subscribeOn(scheduler)
            .observeOn(AndroidSchedulers.mainThread())

    private fun Disposable.addToComposite() = disposable.add(this)

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }

    companion object {
        private const val TAG = "MainViewModel"
    }
}