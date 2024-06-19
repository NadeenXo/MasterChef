package com.example.masterchef

import com.google.gson.annotations.SerializedName

data class Categories(
    @SerializedName("meals") var meals: ArrayList<MealsCategories> = arrayListOf()
)

data class MealsCategories(
    @SerializedName("strCategory") var strCategory: String? = null
)
