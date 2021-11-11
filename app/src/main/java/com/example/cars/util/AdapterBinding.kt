package com.example.cars.util

import android.annotation.SuppressLint
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cars.R
import com.example.cars.model.data.CarData
import com.example.cars.view.CarAdapter

@BindingAdapter("carListData")
fun carListData(recyclerView: RecyclerView, data: List<CarData>?) {
    val adapter = recyclerView.adapter as CarAdapter
    adapter.submitList(data)
}
@SuppressLint("SetTextI18n")
@BindingAdapter("brandData")
fun brandData(txt: TextView, data: String?){
    if (data != null) {
        txt.text  =   "${R.string.Brand} + $data "
    }else{
        txt.text  = null
    }
}
@SuppressLint("SetTextI18n")
@BindingAdapter("isUsedData")
fun isUsedData(txt: TextView, data: Boolean?){
    if (data != null) {
        if(data == true){
            txt.text  = "${R.string.YES}"
        }else{
            txt.text  = "${R.string.NO}"
        }
    }else{
        txt.text  = null
    }
}

@SuppressLint("SetTextI18n")
@BindingAdapter("constructionYear")
fun constructionYear(txt: TextView, data: String?){
    if (data != null) {
        txt.text  =  "${R.string.Construction} + $data "
    }else{
        txt.text  = null
    }
}

@BindingAdapter("picData")
fun picData(img: ImageView, pic: String?){
    if (pic != null){
        val imgUri = pic.toUri().buildUpon().scheme("https").build()
        Glide
            .with(img.context)
            .load(imgUri)
            .error(R.drawable.defaultpp)
            .into(img)
    }else{
        Glide
            .with(img.context)
            .load(R.drawable.defaultpp)
            .into(img)
    }

}
