package com.kaisho.carforrent.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kaisho.carforrent.adapter.FavoriteAdapter
import com.kaisho.carforrent.databinding.FragmentFavoriteBinding
import com.kaisho.carforrent.room.viewModel.FavoriteViewModel
import com.kaisho.carforrent.room.viewModel.SharedViewModel



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
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        setHasOptionsMenu(true)

        favoriteViewModel.getAllData.observe(viewLifecycleOwner, Observer{data ->
            sharedViewModel.checkIfDatabaseEmpty(data)
            adapter.setData(data)
        })

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}