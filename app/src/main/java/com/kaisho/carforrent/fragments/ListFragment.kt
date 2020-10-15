package com.kaisho.carforrent.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.agrawalsuneet.dotsloader.loaders.LazyLoader
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.kaisho.carforrent.R
import com.kaisho.carforrent.adapter.CarAdapter
import com.kaisho.carforrent.model.CarsModel
import com.kaisho.carforrent.model.FavoritePOJO
import com.kaisho.carforrent.presenter.CarsPresenter
import com.kaisho.carforrent.room.viewModel.FavoriteViewModel
import com.kaisho.carforrent.view.CarsView
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator
import java.util.zip.Inflater

class ListFragment : MvpAppCompatFragment(), CarsView{


    //viewModels
    private val favoriteViewModel: FavoriteViewModel by viewModels()

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
        localArrayList.clear()
        localArrayList.addAll(list)
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
        if (item.itemId == R.id.list_menu_favorite){
            findNavController().navigate(R.id.action_listFragment_to_favoriteFragment)
        }
        return super.onOptionsItemSelected(item)
    }
}