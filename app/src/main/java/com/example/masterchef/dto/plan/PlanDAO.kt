package com.example.masterchef.dto.plan

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PlanDAO {
    @Query("SELECT * FROM PlanTable WHERE dayWeek = :dayWeek")
    suspend fun getFavByDay(dayWeek: String): List<PlanTable>

    @Query("SELECT * FROM PlanTable")
    suspend fun getAll(): List<PlanTable>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg favArg: PlanTable): List<Long>

    @Delete
    suspend fun delete(favObj: PlanTable): Int

}
