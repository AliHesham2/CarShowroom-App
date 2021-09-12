package com.example.cars.model.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CarData (
    val id:Int,
    val brand:String?,
    val constractionYear:String?,
    val isUsed:Boolean?,
    val imageUrl:String?
        ): Parcelable