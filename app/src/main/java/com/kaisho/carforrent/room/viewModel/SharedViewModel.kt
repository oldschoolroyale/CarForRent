package com.kaisho.carforrent.room.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.kaisho.carforrent.model.FavoritePOJO

class SharedViewModel(application: Application): AndroidViewModel(application){
    val emptyDatabase: MutableLiveData<Boolean> = MutableLiveData(false)

    fun checkIfDatabaseEmpty(favoriteList: List<FavoritePOJO>){
        emptyDatabase.value = favoriteList.isEmpty()
    }

}