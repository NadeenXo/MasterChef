package com.example.masterchef.dashboard.country

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.masterchef.R
import com.example.masterchef.dashboard.meal.MealAdapter
import com.example.masterchef.dashboard.meal.MealFragment
import com.example.masterchef.network.APIClient
import kotlinx.coroutines.launch

class CountryFragment : Fragment()
//    ,Communicator
{

    private lateinit var rvCountry: RecyclerView
    private lateinit var rvMeals: RecyclerView
    private lateinit var countryAdapter: CountryAdapter
    private lateinit var mealAdapter: MealAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_country, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvCountry = view.findViewById(R.id.rv_countries)
        rvMeals = view.findViewById(R.id.rv_meals_countries)

        fetchCountries()
        fetchMealsByCountry("American")
    }

    private fun fetchCountries() {
        lifecycleScope.launch {
            try {
                val service = APIClient.getInstance()
                val response = service.getAreas()
                if (response.isSuccessful) {
                    val countries = response.body()?.meals ?: emptyList()
                    rvCountry.layoutManager =
                        GridLayoutManager(context, 3)
                    countryAdapter = CountryAdapter(countries) { countryName ->
                        fetchMealsByCountry(countryName)
                    }
                    rvCountry.adapter = countryAdapter
                }
            } catch (e: Exception) {
                // Handle the error
            }
        }
    }

    private fun fetchMealsByCountry(countryName: String) {
        lifecycleScope.launch {
            try {
                val service = APIClient.getInstance()
                val response = service.getMealsByArea(countryName)
                if (response.isSuccessful && response.body() != null) {
                    val meals = response.body()?.meals ?: emptyList()
                    rvMeals.layoutManager = LinearLayoutManager(context)
//                    mealAdapter = MealAdapter(meals, this@CountryFragment)
                    mealAdapter = MealAdapter(meals)
                    rvMeals.adapter = mealAdapter
                }
            } catch (e: Exception) {
                // Handle the error
            }
        }
    }


    private fun navigateToMealFragment() {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.nav_host_fragment_dashboard, MealFragment())
            .addToBackStack("MealFragment")
            .commit()
    }
}
