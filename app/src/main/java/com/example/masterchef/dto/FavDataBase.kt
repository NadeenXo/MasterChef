package com.example.masterchef.dto

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [FavouriteTable::class], version = 1)
abstract class FavDataBase : RoomDatabase() {
    abstract fun favDao(): FavDAO

    companion object {
        @Volatile
        private var INSTANCE: FavDataBase? = null
        fun getInstance(context: Context): FavDataBase {
            return INSTANCE ?: synchronized(this)
            {
                val tempInstance = Room.databaseBuilder(
                    context.applicationContext,
                    FavDataBase::class.java,
                    "Favourite_database"
                ).build()
                INSTANCE = tempInstance
                tempInstance
            }
        }
    }
}
