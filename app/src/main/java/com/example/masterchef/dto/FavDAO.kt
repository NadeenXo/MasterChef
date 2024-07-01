package com.example.masterchef.dto

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavDAO {
    @Query("SELECT * FROM favs WHERE idMeal = :idMeal")
    suspend fun getFavByName(idMeal: String): List<fav>

    @Query("SELECT * FROM favs")
    suspend fun getFavs(): List<fav>


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(vararg favArg: fav): List<Long>

    @Delete
    suspend fun delete(favObj: fav): Int

}
