package com.kaisho.carforrent.binding

import android.annotation.SuppressLint
import android.media.Image
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.navigation.findNavController
import com.kaisho.carforrent.fragments.ListFragmentDirections
import com.kaisho.carforrent.model.CarsModel
import com.squareup.picasso.Picasso

class DataBinding {
    companion object{
        @BindingAdapter("android:emptyDatabase")
        @JvmStatic
        fun emptyDatabase(view: View, emptyDatabase: MutableLiveData<Boolean>){
            when(emptyDatabase.value){
                true -> view.visibility = View.VISIBLE
                false -> view.visibility = View.INVISIBLE
            }
        }
        @BindingAdapter("android:navigateToCheckRent")
        @JvmStatic
        fun navigateToCheckRent(view: Button, carItem: CarsModel){
            view.setOnClickListener {
                    val action = ListFragmentDirections.actionListFragmentToCheckRentFragment(carItem)
                    view.findNavController().navigate(action)
            }
        }
        @BindingAdapter("android:imageUrl")
        @JvmStatic
        fun imageUrl(image: ImageView, carItem: CarsModel){
            carItem.image.let { url -> Picasso.with(image.context).load(url).into(image) }
        }
        @SuppressLint("SetTextI18n")
        @BindingAdapter("android:priceToText")
        @JvmStatic
        fun priceToText(text: TextView, carItem: CarsModel){
            text.text = "$ ${carItem.price} / day"
        }
        @SuppressLint("SetTextI18n")
        @BindingAdapter("android:nameAndDescription")
        @JvmStatic
        fun nameAndDescription(text: TextView, carItem: CarsModel){
            text.text = "${carItem.name}\n${carItem.description}"
        }
    }
}