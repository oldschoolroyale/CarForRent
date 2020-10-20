package com.kaisho.carforrent.presenter

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.kaisho.carforrent.model.CarsModel
import com.kaisho.carforrent.provider.CarsProvider
import com.kaisho.carforrent.view.CarsView

@InjectViewState
class CarsPresenter : MvpPresenter<CarsView>(){
    fun load(days: Int){
        viewState.startLoading()
        CarsProvider(this).parse(days)
    }
    fun error(error: String){
        viewState.endLoading()
        viewState.showError(error)
    }
    fun tryToSetUp(list: ArrayList<CarsModel>){
        viewState.endLoading()
        if (list.isEmpty()){
            viewState.loadEmptyList()
        }
        else{
            viewState.loadList(list)
        }
    }
}