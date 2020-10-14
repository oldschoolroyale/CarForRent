package com.kaisho.carforrent.room.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.kaisho.carforrent.model.FavoritePOJO
import com.kaisho.carforrent.room.FavoriteRoom
import com.kaisho.carforrent.room.repository.FavoriteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteViewModel(application: Application): AndroidViewModel(application) {

    private val favoriteDao = FavoriteRoom.getDatabase(
        application
    ).getFavoriteDao()
    private val repository: FavoriteRepository

    val getAllData: LiveData<List<FavoritePOJO>>

    init {
        repository = FavoriteRepository(favoriteDao)
        getAllData = repository.getAllData
    }

    fun insertData(favoritePOJO: FavoritePOJO){
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertData(favoritePOJO)
        }
    }

    fun updateData(favoritePOJO: FavoritePOJO){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateData(favoritePOJO)
        }
    }

    fun deleteFavorite(favoritePOJO: FavoritePOJO){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteItem(favoritePOJO)
        }
    }
    fun deleteAll(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAll()
        }
    }
}