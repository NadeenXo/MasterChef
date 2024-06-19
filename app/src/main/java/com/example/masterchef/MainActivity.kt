package com.example.masterchef

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.masterchef.network.APIClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
        lateinit var recyclerView: RecyclerView
        lateinit var name: TextView
        lateinit var img: ImageView

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)

            name = findViewById(R.id.tv_meal_name)
            img = findViewById(R.id.iv_meal)

            val service = APIClient.getInstance()
            service.getMeals().enqueue(object : Callback<MealsModel?> {
                override fun onResponse(call: Call<MealsModel?>?, response: Response<MealsModel?>?) {
                    Log.i("Meals", "onResponse: ${response?.body()}")
                    val meals = response?.body()?.meals
                    name.text = meals?.get(0)?.strMeal ?: " "
                    Glide.with(this@MainActivity).load(meals?.get(0)?.strMealThumb).into(img)
                }

                override fun onFailure(call: Call<MealsModel?>?, t: Throwable?) {
                    Log.i("Meals", "onFailure: ${t?.cause}")
                }
            })

            service.getCategories().enqueue(object : Callback<Categories?> {
                override fun onResponse(call: Call<Categories?>?, response: Response<Categories?>?) {
                    Log.i("Categories", "onResponse: ${response?.body()}")
                    val categories = response?.body()?.meals

                    recyclerView = findViewById(R.id.rv)
                    recyclerView.adapter = categories?.let { MealsAdapter(it) }
                    recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
                }

                override fun onFailure(call: Call<Categories?>?, t: Throwable?) {
                    Log.i("Categories", "onFailure: ${t?.cause}")
                }
            })
        }
    }