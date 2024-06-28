package com.example.masterchef.network

import com.example.masterchef.dashboard.CategoriesResponse
import com.example.masterchef.dashboard.MealsResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("random.php")
    suspend fun getMeals(): Response<MealsResponse>

    @GET("list.php?c=list")
    suspend fun getCategories(): Response<CategoriesResponse>

}