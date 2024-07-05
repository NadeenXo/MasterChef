package com.example.masterchef.dashboard.search

import com.example.masterchef.dashboard.meal.Meals
import com.google.gson.annotations.SerializedName


data class RandomMealsResponse (

    @SerializedName("meals" ) var meals : ArrayList<Meals> = arrayListOf()

)