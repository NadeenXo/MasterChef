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
import com.example.masterchef.dashboard.country.model.MealAreaStr
import com.example.masterchef.dashboard.country.view.CountryAdapter
import com.example.masterchef.dashboard.country.view.CountryListener
import com.example.masterchef.dashboard.meal.MealDetailsFragment
import com.example.masterchef.dashboard.meal.view.MealAdapter
import com.example.masterchef.dashboard.meal.view.MealListener
import com.example.masterchef.network.APIClient
import com.example.masterchef.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CountryFragment : Fragment(), CountryListener, MealListener {

    private lateinit var rvCountry: RecyclerView
    private lateinit var rvMeals: RecyclerView
    private lateinit var countryAdapter: CountryAdapter
    private lateinit var mealAdapter: MealAdapter
    private lateinit var service: ApiService

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
        service = APIClient.getInstance()

        fetchCountries()
        fetchMealsByCountry("American")
    }

    private fun fetchCountries() {
        lifecycleScope.launch {
            try {
                val response = service.getAreas()
                if (response.isSuccessful) {
                    val countries = response.body()?.meals ?: emptyList()
                    rvCountry.layoutManager =
                        GridLayoutManager(context, 3)
                    countryAdapter = CountryAdapter(countries, this@CountryFragment)
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
                val response = service.getMealsByArea(countryName)
                if (response.isSuccessful && response.body() != null) {
                    val meals = response.body()?.meals ?: emptyList()
                    rvMeals.layoutManager = LinearLayoutManager(context)
                    mealAdapter = MealAdapter(meals, this@CountryFragment)
                    rvMeals.adapter = mealAdapter
                }
            } catch (e: Exception) {
                // Handle the error
            }
        }
    }

    override fun onClick(country: MealAreaStr) {
        val name = country.strArea
        lifecycleScope.launch(Dispatchers.IO) {
            fetchMealsByCountry(name)
        }
    }

    override fun onClick(id: String) {
        navigateToMealDetailsFragment(id)
    }

    private fun navigateToMealDetailsFragment(mealId: String) {
        val fragment = MealDetailsFragment.newInstance(mealId)
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.nav_host_fragment_dashboard, fragment)
            .addToBackStack("MealDetailsFragment")
            .commit()
    }
}
