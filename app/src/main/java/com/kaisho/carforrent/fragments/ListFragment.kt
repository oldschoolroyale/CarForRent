package com.kaisho.carforrent.fragments

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.util.Pair
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.agrawalsuneet.dotsloader.loaders.LazyLoader
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.kaisho.carforrent.R
import com.kaisho.carforrent.adapter.listAdapter.CarAdapter
import com.kaisho.carforrent.model.CarsModel
import com.kaisho.carforrent.model.FavoritePOJO
import com.kaisho.carforrent.presenter.CarsPresenter
import com.kaisho.carforrent.room.viewModel.FavoriteViewModel
import com.kaisho.carforrent.room.viewModel.SharedViewModel
import com.kaisho.carforrent.view.CarsView
import kotlinx.android.synthetic.main.fragment_list.*
import kotlinx.android.synthetic.main.fragment_list.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ListFragment : MvpAppCompatFragment(), CarsView{


    //viewModels
    private val favoriteViewModel: FavoriteViewModel by viewModels()
    private val sharedViewModel: SharedViewModel by viewModels()

    //local ArrayList
    private var localArrayList: ArrayList<CarsModel> = ArrayList()

    private lateinit var recyclerView: RecyclerView
    private lateinit var loader: LazyLoader
    private lateinit var emptyList: LinearLayout
    private var adapter = CarAdapter(this)

    @InjectPresenter
    lateinit var presenter: CarsPresenter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_list, container, false)

        recyclerView = view.findViewById(R.id.fragmentListRecyclerView)
        loader = view.findViewById(R.id.fragmentListLoader)
        emptyList = view.findViewById(R.id.fragmentListEmpty)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireActivity())
        recyclerView.isFocusable = false
        recyclerView.isNestedScrollingEnabled = false

        val materialDatePicker = sharedViewModel.datePicker().build()

        view.fragmentListCardView.setOnClickListener {
            materialDatePicker.show(requireFragmentManager(), "DATE_PICKER")
        }

        materialDatePicker.addOnPositiveButtonClickListener {
            val model = it.toString().split(" ")
            sharedViewModel.datePick(model)
        }

        val dateFromLiveData : LiveData<String> = sharedViewModel.getDateFrom()

        dateFromLiveData.observe(requireActivity(), androidx.lifecycle.Observer {
            val date = it.split(" ")
            view.fragmentListFromTextView.text = "From \n${date[0]}"
            view.fragmentListUntilTextView.text = "Until \n${date[1]}"
        })





        setHasOptionsMenu(true)
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
        this.localArrayList = list
    }

    override fun favoriteClick(position: Int) {
        favoriteViewModel.insertData(FavoritePOJO(
            0,
            localArrayList[position].image!!,
            localArrayList[position].name!!,
            localArrayList[position].description!!,
            0
        ))
        findNavController().navigate(R.id.action_listFragment_to_favoriteFragment)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.list_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.list_menu_favorite -> findNavController().navigate(R.id.action_listFragment_to_favoriteFragment)
            R.id.list_menu_price_high ->{
                //Collections.sort(localArrayList, CarsModel().highToLow)
                loadList(localArrayList)
            }
            R.id.list_menu_price_low ->{
                //Collections.sort(localArrayList, CarsModel().lowToHigh)
                loadList(localArrayList)
            }
        }

        return super.onOptionsItemSelected(item)
    }
}