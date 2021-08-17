package com.example.cars.view

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.cars.data.CarData
import com.example.cars.service.CarApi
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import java.io.IOException

class CarFragmentViewModel(app: Application):AndroidViewModel(app) {

    private var pagenumber = 0

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

    private val handler = CoroutineExceptionHandler { _, t ->
        if (t is IOException) {
            _error.value = "network Error !! Probably No Internet"
            _loading.value = false
        }
        else {
            _error.value = "SomeThing Went Wrong"
            _loading.value = false
        }
    }

    init {
        callCars()
    }

    fun callCars(){
        _loading.value = true
        viewModelScope.launch(handler) {
            getCarsData()
        }
    }

    private suspend fun getCarsData(){
        pagenumber++
        val response = CarApi.carService.getCarData(pagenumber)
        if(response.isSuccessful){
            _loading.value = false
            val data = response.body()
            if(data != null){
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
        }else{
            _loading.value = false
            _error.value = "SomeThing Went Wrong"
        }
    }
}