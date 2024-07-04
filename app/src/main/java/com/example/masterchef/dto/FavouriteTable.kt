package com.example.masterchef.dto

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "FavouriteTable")
data class FavouriteTable(@PrimaryKey val idMeal: String, val strMeal: String, val strMealThumb: String)
