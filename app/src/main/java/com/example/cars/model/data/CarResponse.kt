package com.example.cars.model.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CarResponse (
    val status : Int ,
    val data:List<CarData>
        ): Parcelable