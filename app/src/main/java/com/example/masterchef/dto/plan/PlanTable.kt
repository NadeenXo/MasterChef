package com.example.masterchef.dto.plan

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "PlanTable")
data class PlanTable(
    @PrimaryKey val dayWeek: String,
    val breakfast: String,
    val lunch: String,
    val dinner: String
)
