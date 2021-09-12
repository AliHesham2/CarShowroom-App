package com.example.cars.view

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.cars.model.data.CarData
import com.example.cars.model.data.CarResponse
import com.example.cars.model.service.CarApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineExceptionHandler
import java.io.IOException
import java.util.*

class CarFragmentViewModel(app: Application):AndroidViewModel(app) {

    private var pagenumber = 0
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
        pagenumber++
          compositeDisposable.add( CarApi.carService.getCarData(pagenumber)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ s -> onSuccess(s)},{ t -> onFailure(t)  }))
    }

    private fun onFailure(t: Throwable) {
        Log.i("Error","Error")
        if (t is IOException) {
            _error.value = "network Error !! Probably No Internet"
            _loading.value = false
        }
        else {
            _error.value = "SomeThing Went Wrong"
            _loading.value = false
        }
    }

    private fun onSuccess(data: CarResponse) {
        Log.i("Sucess","Sucess")
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