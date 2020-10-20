package com.kaisho.carforrent.fragments

import android.os.Bundle
import android.view.*
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.simplepass.loadingbutton.customViews.CircularProgressButton
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.kaisho.carforrent.R
import com.kaisho.carforrent.adapter.listAdapter.CarAdapter
import com.kaisho.carforrent.model.CarsModel
import com.kaisho.carforrent.model.FavoritePOJO
import com.kaisho.carforrent.presenter.CarsPresenter
import com.kaisho.carforrent.view.CarsView
import com.kaisho.carforrent.viewModel.FavoriteViewModel
import com.kaisho.carforrent.viewModel.ListViewModel
import com.kaisho.carforrent.viewModel.SharedViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator
import kotlinx.android.synthetic.main.fragment_list.view.*


class ListFragment : MvpAppCompatFragment(), CarsView{


    //viewModels
    private val favoriteViewModel: FavoriteViewModel by viewModels()
    //private val sharedViewModel: SharedViewModel by viewModels()
    private val listViewModel: ListViewModel by viewModels()

    //local ArrayList
    private var localArrayList: ArrayList<CarsModel> = ArrayList()

    private lateinit var recyclerView: RecyclerView
    private lateinit var loader: CircularProgressButton
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
        loader = view.findViewById(R.id.fragmentListAnimButtonSearch)
        emptyList = view.findViewById(R.id.fragmentListEmpty)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireActivity())
        recyclerView.itemAnimator = SlideInUpAnimator().apply {
            addDuration = 300
        }
        recyclerView.isFocusable = false
        recyclerView.isNestedScrollingEnabled = false

        val materialDatePicker = listViewModel.datePicker().build()

        view.fragmentListCardView.setOnClickListener {
            materialDatePicker.show(requireFragmentManager(), "DATE_PICKER")
        }

        materialDatePicker.addOnPositiveButtonClickListener {
            val model = it.toString().split(" ")
            listViewModel.datePick(model)
            //subscribeOnTotalCount(view, model)
        }

        val dateFromLiveData : LiveData<String> = listViewModel.getDateFrom()
        val daysIntList: LiveData<Int> = listViewModel.getDaysInt()

        dateFromLiveData.observe(requireActivity(), Observer {
            val date = it.split(" ")
            view.fragmentListFromTextView.text = "From \n${date[0]}"
            view.fragmentListUntilTextView.text = "Until \n${date[1]}"
        })
        daysIntList.observe(requireActivity(), Observer {
            view.fragmentListTotalDaysTextView.text = "$it days"
        })

        setHasOptionsMenu(true)
        loader.setOnClickListener {
            presenter.load(listViewModel.daysInt)

        }
        return view
    }

    private fun subscribeOnTotalCount(view: View, model: List<String>) {
        /*val dispose = listViewModel.calculate(model)
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({

            },{

            })*/
    }

    override fun startLoading() {
        loader.startAnimation()
        recyclerView.visibility = View.INVISIBLE
        emptyList.visibility = View.INVISIBLE
    }

    override fun endLoading() {
        loader.revertAnimation()
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