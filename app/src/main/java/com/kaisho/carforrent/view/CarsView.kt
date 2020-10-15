package com.kaisho.carforrent.view

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.kaisho.carforrent.model.CarsModel

@StateStrategyType(value = AddToEndSingleStrategy::class)
interface CarsView: MvpView {
    fun startLoading()
    fun endLoading()
    fun showError(error: String)
    fun loadEmptyList()
    fun loadList(list: ArrayList<CarsModel>)
    fun favoriteClick(position: Int)
}