package com.example.masterchef.dashboard.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.masterchef.R
import com.example.masterchef.dashboard.Categories
import com.example.masterchef.dashboard.MealsAdapter
import com.example.masterchef.dashboard.MealsModel
import com.example.masterchef.network.APIClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {
    lateinit var recyclerView: RecyclerView
    lateinit var name: TextView
    lateinit var img: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        name = view.findViewById(R.id.tv_meal_name)
        img = view.findViewById(R.id.iv_meal)
        recyclerView = view.findViewById(R.id.rv)


        val service = APIClient.getInstance()
        service.getMeals().enqueue(object : Callback<MealsModel?> {
            override fun onResponse(call: Call<MealsModel?>?, response: Response<MealsModel?>?) {
                Log.i("Meals", "onResponse: ${response?.body()}")
                val meals = response?.body()?.meals
                name.text = meals?.get(0)?.strMeal ?: " "
                if (isAdded) {
                    Glide.with(this@HomeFragment).load(meals?.get(0)?.strMealThumb).into(img)
                }
            }

            override fun onFailure(call: Call<MealsModel?>?, t: Throwable?) {
                Log.i("Meals", "onFailure: ${t?.cause}")
            }
        })

        service.getCategories().enqueue(object : Callback<Categories?> {
            override fun onResponse(call: Call<Categories?>?, response: Response<Categories?>?) {
                Log.i("Categories", "onResponse: ${response?.body()}")
                val categories = response?.body()?.meals

                recyclerView.layoutManager = LinearLayoutManager(activity)
                recyclerView.adapter = categories?.let { MealsAdapter(it) }
            }

            override fun onFailure(call: Call<Categories?>?, t: Throwable?) {
                Log.i("Categories", "onFailure: ${t?.cause}")
            }
        })
        return view
    }
}