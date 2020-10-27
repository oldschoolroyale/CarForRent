package com.kaisho.carforrent.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.kaisho.carforrent.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_check_rent.view.*

class CheckRentFragment : Fragment() {
    private val args by navArgs<CheckRentFragmentArgs>()

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_check_rent, container, false)

        args.carItem.image.let { url ->
            Picasso.with(context).load(url).into(view.fragmentCheckRentImage)
        }
            //view.fragmentCheckRentName.text = "${args.carItem.name}\n${args.carItem.description}\n${args.carItem.manual}\n${args.carItem.mapTag}\n${args.carItem.gasStation}"
        return view
    }
}