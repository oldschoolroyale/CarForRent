package com.kaisho.carforrent.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_table")
data class FavoritePOJO(
    @PrimaryKey(autoGenerate = true)
    var id : Int,
    var image: String,
    var name: String,
    var description: String,
    var price: Int
)