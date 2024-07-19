package com.example.masterchef.dashboard.country

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.masterchef.R
import com.example.masterchef.dashboard.country.view.CountryListener
import com.example.masterchef.dashboard.meal.MealDetailsFragment
import com.example.masterchef.dashboard.meal.view.MealAdapter
import com.example.masterchef.dashboard.meal.view.MealListener
import com.example.masterchef.network.APIClient
import com.example.masterchef.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CountryFragment : Fragment(), CountryListener, MealListener {

    private lateinit var autoCompleteTextViewCountry: AutoCompleteTextView
    private lateinit var rvMeals: RecyclerView

    private lateinit var mealAdapter: MealAdapter
    private lateinit var service: ApiService
    private lateinit var listener: CountryListener

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_country, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvMeals = view.findViewById(R.id.rv_meals_countries)
        autoCompleteTextViewCountry = view.findViewById(R.id.autoCompleteTextView_country)
        service = APIClient.getInstance()
        listener = this

        fetchCountries()
        fetchMealsByCountry("American")
        autoCompleteTextViewCountry.setOnItemClickListener { parent, _, position, _ ->
            val selectedCountry = parent.getItemAtPosition(position)
            listener.onClickCountry(selectedCountry.toString())
        }

    }

    private fun fetchCountries() {
        val countryList = mutableListOf<String>()

        lifecycleScope.launch {
            try {
                val response = service.getAreas()
                if (response.isSuccessful) {
                    val countries = response.body()?.meals ?: emptyList()
                    for (country in countries) {
                        countryList.add(country.strArea)
                    }
                }
                val arrayAdapter = ArrayAdapter(
                    requireContext(),
                    R.layout.item_country,
                    countryList
                )
                autoCompleteTextViewCountry.setAdapter(arrayAdapter)
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

    override fun onClickCountry(countryName: String) {
        lifecycleScope.launch(Dispatchers.IO) {
            fetchMealsByCountry(countryName)
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
