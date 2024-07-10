package com.example.masterchef.dashboard.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.masterchef.R
import com.example.masterchef.dashboard.home.model.category.Category
import com.example.masterchef.dashboard.home.view.CategoriesAdapter
import com.example.masterchef.dashboard.home.view.CategoryListener
import com.example.masterchef.dashboard.meal.MealFragment
import com.example.masterchef.dashboard.meal.view.MealAdapter
import com.example.masterchef.network.APIClient
import com.example.masterchef.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class HomeFragment : Fragment(), CategoryListener {
    private lateinit var categoryName: String
    private lateinit var recyclerView: RecyclerView
    private lateinit var name: TextView
    private lateinit var img: ImageView
    private lateinit var mealCard: View
    private lateinit var service: ApiService
    private lateinit var categorySearch: SearchView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        name = view.findViewById(R.id.tv_meal_name_card)
        img = view.findViewById(R.id.iv_meal_card)
        recyclerView = view.findViewById(R.id.rv_home)
        mealCard = view.findViewById(R.id.meal_card_home)
        categorySearch = view.findViewById(R.id.sv_categories)


        service = APIClient.getInstance()

        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val mealsResponse = service.getMeals()
                if (mealsResponse.isSuccessful) {
                    val meals = mealsResponse.body()?.meals
                    withContext(Dispatchers.Main) {
                        if (!meals.isNullOrEmpty()) {
                            name.text = meals[0].strMeal
                            if (isAdded) {
                                Glide.with(this@HomeFragment).load(meals[0].strMealThumb)
                                    .centerCrop().error(R.drawable.img_onboarding).into(img)
                            }
                        }
                    }
                } else {
                    Log.e("HomeFragment", "Error: ${mealsResponse.code()}")
                }
            } catch (e: Exception) {
                Log.e("HomeFragment", "Exception: ${e.message}")
            }
        }

        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val categoriesResponse = service.getMealCategories()
                if (categoriesResponse.isSuccessful) {
                    val categories = categoriesResponse.body()?.categories
                    withContext(Dispatchers.Main) {
                        recyclerView.layoutManager =
                            GridLayoutManager(requireActivity(), 2, RecyclerView.VERTICAL, false)
                        recyclerView.adapter =
                            categories?.let { CategoriesAdapter(it, this@HomeFragment) }
                    }
                } else {
                    Log.e("HomeFragment", "Error: ${categoriesResponse.code()}")
                }
            } catch (e: Exception) {
                Log.e("HomeFragment", "Exception: ${e.message}")
            }
        }
        categorySearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                TODO("Not yet implemented")
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                TODO("Not yet implemented")
            }
        })
        return view
    }

    override fun onClick(category: Category) {
        categoryName = category.strCategory
        navigateToMealFragment(categoryName)
    }

    private fun navigateToMealFragment(categoryName: String) {
        val fragment = MealFragment.newInstance(categoryName)
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.nav_host_fragment_dashboard, fragment)
            .addToBackStack("MealFragment")
            .commit()
    }

    private fun fetchMealsByCategories(categoryName: String) {
        lifecycleScope.launch {
            try {
                val response = service.getMealsByCategory(categoryName)
                if (response.isSuccessful && response.body() != null) {
                    val meals = response.body()?.meals
//                     recyclerView.layoutManager = LinearLayoutManager(context)
//                    mealAdapter = MealAdapter(meals, this@CountryFragment)
//                    mealAdapter = MealAdapter(meals)
//                    rvMeals.adapter = mealAdapter
                    Toast.makeText(context, "$meals", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                // Handle the error
            }
        }
    }


//    override fun onClick(category: Category) {
//        categoryName = category.strCategory
//        lifecycleScope.launch(Dispatchers.IO) {
//          //  fetchMealsByCategories(categoryName)
////            withContext(Dispatchers.Main) {
////                Toast.makeText(context, categoryName, Toast.LENGTH_SHORT).show()
////            }
//        }
//        navigateToMealFragment()
//    }

    private fun navigateToMealFragment1() {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.nav_host_fragment_dashboard, MealFragment())
            .addToBackStack("MealFragment")
            .commit()
    }
}