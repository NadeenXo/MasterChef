package com.example.masterchef.network

import com.example.masterchef.dashboard.country.data.CountryResponse
import com.example.masterchef.dashboard.home.CategoriesStrResponse
import com.example.masterchef.dashboard.home.category.CategoriesResponse
import com.example.masterchef.dashboard.meal.MealsResponse
import com.example.masterchef.dashboard.search.RandomMealsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("random.php")
    suspend fun getMeals(): Response<MealsResponse>

    //categories names only
    @GET("list.php?c=list")
    suspend fun getCategories(): Response<CategoriesStrResponse>

    //categories with all details
    @GET("categories.php")
    suspend fun getMealCategories(): Response<CategoriesResponse>

    @GET("randomselection.php")
    suspend fun getRandomMeals(): Response<RandomMealsResponse>

    //todo
    //filter.php?c=Seafood
    @GET("filter.php")
    suspend fun getMealsByCategory(@Query("c") category: String): Response<MealsResponse>
    @GET("filter.php")
    suspend fun getMealsByArea(@Query("a") area: String): Response<MealsResponse>

    @GET("list.php?a=list")
    suspend fun getAreas(): Response<CountryResponse>

    //Search meal by name
    //www.themealdb.com/api/json/v1/1/search.php?s=Arrabiata
    //
    //List all meals by first letter
    //www.themealdb.com/api/json/v1/1/search.php?f=a
    //
    //Lookup full meal details by id
    //www.themealdb.com/api/json/v1/1/lookup.php?i=52772
    //
    //Filter by main ingredient
    //www.themealdb.com/api/json/v1/1/filter.php?i=chicken_breast
    //
    //Filter by Category
    //www.themealdb.com/api/json/v1/1/filter.php?c=Seafood
    //
    //Filter by Area
    //www.themealdb.com/api/json/v1/1/filter.php?a=Canadian
}