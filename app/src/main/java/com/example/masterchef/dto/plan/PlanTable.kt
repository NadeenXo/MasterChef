package com.example.masterchef.dto.plan

import androidx.room.Entity

@Entity(tableName = "PlanTable", primaryKeys = ["dayWeek", "type"])
data class PlanTable(
    val dayWeek: String,
    val type: String,
    val mealName: String
)
