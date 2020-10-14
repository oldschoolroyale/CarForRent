package com.kaisho.carforrent.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kaisho.carforrent.R
import com.kaisho.carforrent.model.CarsModel
import com.kaisho.carforrent.view.CarsView
import com.squareup.picasso.Picasso

class CarAdapter(var carsView: CarsView): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val carsArrayList: ArrayList<CarsModel> = ArrayList()

    fun newCars(newCarsArrayList: ArrayList<CarsModel>) {
        carsArrayList.clear()
        carsArrayList.addAll(newCarsArrayList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CarsViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.car_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return carsArrayList.count()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is CarsViewHolder) {
            holder.bind(carsArrayList[position], carsView)
        }
    }

    private class CarsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private var image = view.findViewById<ImageView>(R.id.carItemCarsImage)
        private var name = view.findViewById<TextView>(R.id.carItemCarsNameTView)
        private var favoriteButton = view.findViewById<Button>(R.id.button1)
        fun bind(carsModel: CarsModel, carsView: CarsView) {
            carsModel.image.let { url ->
                Picasso.with(itemView.context).load(url).into(image)
            }
            name.text = "${carsModel.name}\n${carsModel.description}"
            favoriteButton.setOnClickListener {
                carsView.favoriteClick(adapterPosition)
            }
        }
    }
}