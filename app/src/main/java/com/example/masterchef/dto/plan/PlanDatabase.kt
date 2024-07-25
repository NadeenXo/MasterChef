package com.example.masterchef.dto.plan

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [PlanTable::class], version = 1)
abstract class PlanDatabase : RoomDatabase() {
    abstract fun planDAO(): PlanDAO

    companion object {
        @Volatile
        private var INSTANCE: PlanDatabase? = null
        fun getInstance(context: Context): PlanDatabase {
            return INSTANCE ?: synchronized(this)
            {
                val tempInstance = Room.databaseBuilder(
                    context.applicationContext,
                    PlanDatabase::class.java,
                    "Plan_database"
                ).build()
                INSTANCE = tempInstance
                tempInstance
            }
        }
    }
}
