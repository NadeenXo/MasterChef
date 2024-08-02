package com.example.masterchef.dto.plan

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "PlanTable")
data class PlanTable(
    @PrimaryKey val dayWeek: String,
    val type: String,
    val mealName: String,
)
