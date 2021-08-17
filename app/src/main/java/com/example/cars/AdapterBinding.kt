package com.example.cars

import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cars.data.CarData
import com.example.cars.view.CarAdapter

@BindingAdapter("CarlistData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<CarData>?) {
    val adapter = recyclerView.adapter as CarAdapter
    adapter.submitList(data)
}

@BindingAdapter("brandData")
fun brandData(txt: TextView, data: String?){
    if (data != null) {
        txt.text  = "Brand: $data"
    }else{
        txt.text  = null
    }
}

@BindingAdapter("isUsedData")
fun isUsedData(txt: TextView, data: String?){
    if (data != null) {
        txt.text  = "Brand: $data"
    }else{
        txt.text  = null
    }
}

@BindingAdapter("ConstructionYear")
fun ConstructionYear(txt: TextView, data: String?){
    if (data != null) {
        txt.text  = "ConstructionYear: $data"
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
            .circleCrop()
            .error(R.drawable.defaultpp)
            .into(img)
    }else{
        Glide
            .with(img.context)
            .load(R.drawable.defaultpp)
            .circleCrop()
            .into(img)
    }

}
