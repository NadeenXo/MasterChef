package com.example.masterchef.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object APIClient {
    private const val BASE_URL = "https://www.themealdb.com/api/json/v1/1/"

    //    val is initialized only the first time the fun is called
    //    and the same instance is used every time you try to access the fun
    fun getInstance(): ApiService {
        return retrofitInstance
    }

    private val retrofitInstance: ApiService by lazy {
        Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(ApiService::class.java)
    }

}
