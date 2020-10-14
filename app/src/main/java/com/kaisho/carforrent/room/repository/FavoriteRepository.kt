package com.kaisho.carforrent.room.repository

import androidx.lifecycle.LiveData
import com.kaisho.carforrent.model.FavoritePOJO
import com.kaisho.carforrent.room.view.FavoriteDao

class FavoriteRepository(private val favoriteDao: FavoriteDao) {
    val getAllData: LiveData<List<FavoritePOJO>> = favoriteDao.getAllData()

    suspend fun insertData(favoritePOJO: FavoritePOJO){
        favoriteDao.insertData(favoritePOJO)
    }

    suspend fun updateData(favoritePOJO: FavoritePOJO){
        favoriteDao.updateData(favoritePOJO)
    }

    suspend fun deleteItem(favoritePOJO: FavoritePOJO){
        favoriteDao.deleteItem(favoritePOJO)
    }

    suspend fun deleteAll(){
        favoriteDao.deleteAll()
    }
}