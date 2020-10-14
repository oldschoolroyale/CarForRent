package com.kaisho.carforrent.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.agrawalsuneet.dotsloader.loaders.LazyLoader
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.kaisho.carforrent.R
import com.kaisho.carforrent.adapter.CarAdapter
import com.kaisho.carforrent.model.CarsModel
import com.kaisho.carforrent.presenter.CarsPresenter
import com.kaisho.carforrent.view.CarsView

class ListFragment : MvpAppCompatFragment(), CarsView{


    private lateinit var recyclerView: RecyclerView
    private lateinit var loader: LazyLoader
    private lateinit var emptyList: LinearLayout
    private var adapter = CarAdapter()

    @InjectPresenter
    lateinit var presenter: CarsPresenter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_list, container, false)

        (activity as AppCompatActivity).supportActionBar?.hide()

        recyclerView = view.findViewById(R.id.fragmentListRecyclerView)
        loader = view.findViewById(R.id.fragmentListLoader)
        emptyList = view.findViewById(R.id.fragmentListEmpty)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        recyclerView.setHasFixedSize(true)


        presenter.load()
        return view
    }

    override fun startLoading() {
        loader.visibility = View.VISIBLE
        recyclerView.visibility = View.INVISIBLE
        emptyList.visibility = View.INVISIBLE
    }

    override fun endLoading() {
        loader.visibility = View.INVISIBLE
    }

    override fun showError(error: String) {
        Toast.makeText(context, error, Toast.LENGTH_LONG).show()
    }

    override fun loadEmptyList() {
        emptyList.visibility = View.VISIBLE
    }

    override fun loadList(list: ArrayList<CarsModel>) {
        recyclerView.visibility = View.VISIBLE
        adapter.newCars(list)
    }
}