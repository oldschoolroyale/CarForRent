package com.kaisho.carforrent.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.kaisho.carforrent.R
import com.kaisho.carforrent.adapter.FavoriteAdapter
import com.kaisho.carforrent.adapter.SwipeToDelete
import com.kaisho.carforrent.databinding.FragmentFavoriteBinding
import com.kaisho.carforrent.model.FavoritePOJO
import com.kaisho.carforrent.room.viewModel.FavoriteViewModel
import com.kaisho.carforrent.room.viewModel.SharedViewModel
import jp.wasabeef.recyclerview.animators.SlideInRightAnimator
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator


class FavoriteFragment : Fragment() {

    //viewModel
    private val sharedViewModel: SharedViewModel by viewModels()
    private val favoriteViewModel: FavoriteViewModel by viewModels()

    private lateinit var recyclerView: RecyclerView
    private val adapter : FavoriteAdapter by lazy { FavoriteAdapter() }

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.sharedViewModel = sharedViewModel

        recyclerView = binding.fragmentFavoriteRecycler
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireActivity())
        recyclerView.itemAnimator = SlideInUpAnimator().apply {
            addDuration = 300
        }

        //swipeToDelete
        swipeToDelete(recyclerView)
        setHasOptionsMenu(true)

        favoriteViewModel.getAllData.observe(viewLifecycleOwner, Observer{data ->
            sharedViewModel.checkIfDatabaseEmpty(data)
            adapter.setData(data)
        })

        return binding.root
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.favorite_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.favorite_menu_clear_all){
            removeAll()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun removeAll() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Да"){_, _ ->
            favoriteViewModel.deleteAll()
        }
        builder.setNegativeButton("Нет"){_, _ ->}
        builder.setTitle("Вы хотите очистить весь список?")
        builder.setMessage("После удаления нельзя будет вернуть записи")
        builder.create().show()
    }

    private fun swipeToDelete(recyclerView: RecyclerView){
        val swipeToDeleteCallBack = object: SwipeToDelete(){
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val deletedItem = adapter.favoriteList[viewHolder.adapterPosition]
                favoriteViewModel.deleteFavorite(deletedItem)
                adapter.notifyItemRemoved(viewHolder.adapterPosition)

                restoreDeletedData(viewHolder.itemView, deletedItem, viewHolder.adapterPosition)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallBack)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    private fun restoreDeletedData(view: View, deletedItem: FavoritePOJO, position: Int){
        val snackBar = Snackbar.make(
            view, "Удалить '${deletedItem.name}'?",
            Snackbar.LENGTH_LONG
        )
        snackBar.setAction("Отменить"){
            favoriteViewModel.insertData(deletedItem)
            adapter.notifyItemChanged(position)
        }
        snackBar.show()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}