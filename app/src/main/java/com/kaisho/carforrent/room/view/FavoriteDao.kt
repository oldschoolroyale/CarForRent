package com.kaisho.carforrent.room.view

import androidx.lifecycle.LiveData
import androidx.room.*
import com.kaisho.carforrent.model.FavoritePOJO

@Dao
interface FavoriteDao {
    @Query("SELECT * FROM favorite_table ORDER BY id ASC")
    fun getAllData(): LiveData<List<FavoritePOJO>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertData(favoritePOJO: FavoritePOJO)

    @Update
    suspend fun updateData(favoritePOJO: FavoritePOJO)

    @Delete
    suspend fun deleteItem(favoritePOJO: FavoritePOJO)

    @Query("DELETE FROM favorite_table")
    suspend fun deleteAll()
}