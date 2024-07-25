package com.example.masterchef.dto.fav

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavDAO {
    @Query("SELECT * FROM FavouriteTable WHERE idMeal = :idMeal")
    suspend fun getFavById(idMeal: String): List<FavouriteTable>

    @Query("SELECT * FROM FavouriteTable")
    suspend fun getAll(): List<FavouriteTable>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(vararg favArg: FavouriteTable): List<Long>

    @Delete
    suspend fun delete(favObj: FavouriteTable): Int

}
