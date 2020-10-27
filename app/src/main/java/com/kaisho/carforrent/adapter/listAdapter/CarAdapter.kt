package com.kaisho.carforrent.adapter.listAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kaisho.carforrent.databinding.CarItemBinding
import com.kaisho.carforrent.model.CarsModel
import com.kaisho.carforrent.view.CarsView

class CarAdapter(var carsView: CarsView): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val carsArrayList: ArrayList<CarsModel> = ArrayList()

    fun newCars(newCarsArrayList: ArrayList<CarsModel>) {
        carsArrayList.clear()
        carsArrayList.addAll(newCarsArrayList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CarsViewHolder.from(parent)
    }

    override fun getItemCount(): Int {
        return carsArrayList.count()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is CarsViewHolder) {
            holder.bind(carsArrayList[position], carsView)
        }
    }

    private class CarsViewHolder(private val binding: CarItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(carsModel: CarsModel, carsView: CarsView){
            binding.model = carsModel
            binding.carItemButtonAddToFavorite.setOnClickListener {
                carsView.favoriteClick(adapterPosition)
            }
            binding.executePendingBindings()
        }

        companion object{
            fun from(parent: ViewGroup): CarsViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = CarItemBinding.inflate(layoutInflater, parent, false)
                return CarsViewHolder(binding)
            }
        }

    }

}