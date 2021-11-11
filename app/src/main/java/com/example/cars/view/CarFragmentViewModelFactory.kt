package com.example.cars.view

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.cars.R

class CarFragmentViewModelFactory (private val application: Application): ViewModelProvider.Factory {
    @Suppress(("unchecked_cast"))
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CarFragmentViewModel::class.java)) {
            return CarFragmentViewModel(application) as T
        }
        throw IllegalArgumentException(application.resources.getString(R.string.UN_KNOWN_CLASS))
    }
}