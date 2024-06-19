package com.example.masterchef

import com.google.gson.annotations.SerializedName


data class MealsModel(
    @SerializedName("meals") var meals: ArrayList<Meals> = arrayListOf()
)