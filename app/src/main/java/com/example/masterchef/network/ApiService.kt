package com.example.masterchef.network

import com.example.masterchef.dashboard.Categories
import com.example.masterchef.dashboard.MealsModel
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("random.php")
    fun getMeals(): Call<MealsModel>

    @GET("list.php?c=list")
    fun getCategories(): Call<Categories>

}