package com.example.masterchef.dashboard

import com.google.gson.annotations.SerializedName

data class CategoriesResponse(
    @SerializedName("meals") var meals: List<MealsCategories>
)

data class MealsCategories(
    @SerializedName("strCategory") var strCategory: String? = null
)
