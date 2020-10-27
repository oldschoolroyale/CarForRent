package com.kaisho.carforrent.adapter.listAdapter

import android.annotation.SuppressLint
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

    fun filter(text: String){

    }

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
        private var favoriteButton = view.findViewById<Button>(R.id.carItemButtonAddToFavorite)
        private var price = view.findViewById<TextView>(R.id.carItemTViewPrice)
        private var checkRentClick = view.findViewById<Button>(R.id.carItemButtonCheckCart)
        private var airCondition = view.findViewById<TextView>(R.id.carItemTViewAirCondition)
        private var manual = view.findViewById<TextView>(R.id.carItemTViewManual)
        private var mapTagText = view.findViewById<TextView>(R.id.carItemTViewMapTag)
        private var gasStation = view.findViewById<TextView>(R.id.carItemTViewGasStation)
        @SuppressLint("SetTextI18n")
        fun bind(carsModel: CarsModel, carsView: CarsView) {
            carsModel.image.let { url ->
                Picasso.with(itemView.context).load(url).into(image)
            }
            name.text = "${carsModel.name}\n${carsModel.description}"
            favoriteButton.setOnClickListener {
                carsView.favoriteClick(adapterPosition)
            }
            checkRentClick.setOnClickListener {
                carsView.checkRentClick(adapterPosition)
            }
            manual.text = carsModel.manual
            mapTagText.text = carsModel.mapTag


            gasStation.text = carsModel.gasStation
            airCondition.text = carsModel.airCondition
            price.text = "$" + carsModel.price + " / day"
        }
    }

}