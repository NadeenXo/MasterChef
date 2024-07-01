package com.example.masterchef.dto

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "favs")
data class fav(@PrimaryKey val idMeal: String, val strMeal: String)
