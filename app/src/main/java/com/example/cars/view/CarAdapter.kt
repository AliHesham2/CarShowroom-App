package com.example.cars.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.cars.model.data.CarData
import com.example.cars.databinding.CarListBinding

class CarAdapter(private val onClickListener: OnClickListener) :
    ListAdapter<CarData, CarAdapter.ViewHolder>(DiffCallback) {

    class ViewHolder(private var binding: CarListBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(carInfo: CarData) {
            binding.viewModel = carInfo
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<CarData>() {
        override fun areItemsTheSame(oldItem: CarData, newItem: CarData): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: CarData, newItem: CarData): Boolean {
            return oldItem.id == newItem.id
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): ViewHolder {
        return ViewHolder(CarListBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val carInfo = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(carInfo)
        }
        holder.bind(carInfo)
    }

    class OnClickListener(val clickListener: (carInfo: CarData) -> Unit) {
        fun onClick(carInfo: CarData) = clickListener(carInfo)
    }



}