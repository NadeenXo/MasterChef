package com.example.masterchef.dashboard.home

import com.google.gson.annotations.SerializedName

data class CategoriesStrResponse(
    @SerializedName("meals") var meals: List<CategoriesStr>
)

data class CategoriesStr(
    @SerializedName("strCategory") var strCategory: String? = null
)
