package com.example.cars.view

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.cars.R
import com.example.cars.model.data.CarData
import com.example.cars.model.data.CarResponse
import com.example.cars.model.service.CarApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.io.IOException

class CarFragmentViewModel(private val app: Application):AndroidViewModel(app) {

    private var pageNumber = 0
    private  val compositeDisposable = CompositeDisposable()

    private val _carData =  MutableLiveData<List<CarData>>()
    val carData: LiveData<List<CarData>>
        get() = _carData

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean>
        get() = _loading

    private var _loadMore = MutableLiveData<Boolean>()
    val loadMore: LiveData<Boolean>
        get() = _loadMore


    private val _error= MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    init {
        callCars()
    }

    fun callCars(){
        _loading.value = true
        pageNumber++
          compositeDisposable.add( CarApi.carService.getCarData(pageNumber)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ s -> onSuccess(s)},{ t -> onFailure(t)  }))
    }

    private fun onFailure(t: Throwable) {
        if (t is IOException) {
            _error.value = app.resources.getString(R.string.NO_INTERNET)
            _loading.value = false
        }
        else {
            _error.value = app.resources.getString(R.string.WRONG)
            _loading.value = false
        }
    }

    private fun onSuccess(data: CarResponse) {
        _loading.value = false
            val checkIfEmpty = data.data
            if(!checkIfEmpty.isNullOrEmpty()) {
                if(_carData.value.isNullOrEmpty()){
                    _carData.value = data.data
                    _loadMore.value = true
                }else{
                    _carData.value = _carData.value!!.plus(data.data)
                    _loadMore.value = true
                }
            } else{
                _loadMore.value = false
            }

    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

}