package com.kaisho.carforrent.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.kaisho.carforrent.model.FavoritePOJO
import com.kaisho.carforrent.room.view.FavoriteDao

@Database(entities = [FavoritePOJO::class], version = 1, exportSchema = false)
abstract class FavoriteRoom : RoomDatabase() {

    abstract fun getFavoriteDao(): FavoriteDao

    companion object{
        @Volatile
        private var INSTANCE: FavoriteRoom? = null

        fun getDatabase(context: Context): FavoriteRoom{
            val tempInstance = INSTANCE
            if (tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FavoriteRoom::class.java,
                    "favoriteDB"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}