package com.kaisho.carforrent.room

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.kaisho.carforrent.room.view.FavoriteDao

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