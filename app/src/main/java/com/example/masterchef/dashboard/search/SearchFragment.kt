package com.example.masterchef.dashboard.search

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.SearchView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.masterchef.R
import com.example.masterchef.dashboard.meal.MealDetailsFragment
import com.example.masterchef.dashboard.meal.view.MealAdapter
import com.example.masterchef.dashboard.meal.view.MealListener
import com.example.masterchef.network.APIClient
import com.example.masterchef.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchFragment : Fragment(), MealListener {
    private lateinit var rv: RecyclerView
    private lateinit var search: SearchView
    private lateinit var service: ApiService
    private lateinit var img: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_search, container, false)

        rv = view.findViewById(R.id.rv_search)
        search = view.findViewById(R.id.searchView)
        img = view.findViewById(R.id.iv_search_empty)
        service = APIClient.getInstance()

        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrEmpty()) {
                    searchWithCategory(query)
                    searchWithArea(query)
                    searchWithIngredients(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })

        toggleEmptyView(true)

        return view
    }

    private fun toggleEmptyView(show: Boolean) {
        if (show) {
            img.visibility = View.VISIBLE
            rv.visibility = View.GONE
        } else {
            img.visibility = View.GONE
            rv.visibility = View.VISIBLE
        }
    }

    private fun searchWithIngredients(query: String?) {
        // TODO: Implement Ingredients Search
    }

    private fun searchWithArea(countryName: String?) {
        lifecycleScope.launch {
            try {
                val response = countryName?.let { service.getMealsByArea(it) }
                if (response != null && response.isSuccessful && response.body() != null) {
                    val meals = response.body()?.meals ?: emptyList()
                    withContext(Dispatchers.Main) {
                        rv.layoutManager = LinearLayoutManager(context)
                        rv.adapter = MealAdapter(meals, this@SearchFragment)
                        toggleEmptyView(meals.isEmpty())
                    }
                } else {
                    toggleEmptyView(true)
                }
            } catch (e: Exception) {
                Log.e("SearchFragment", "Error fetching meals by area", e)
                toggleEmptyView(true)
            }
        }
    }

    private fun searchWithCategory(categoryName: String?) {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val response = categoryName?.let { service.getMealsByCategory(it) }
                if (response != null && response.isSuccessful) {
                    val meals = response.body()?.meals
                    withContext(Dispatchers.Main) {
                        rv.layoutManager = LinearLayoutManager(context)
                        rv.adapter = MealAdapter(meals ?: emptyList(), this@SearchFragment)
                        toggleEmptyView(meals.isNullOrEmpty())
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        toggleEmptyView(true)
                    }
                }
            } catch (e: Exception) {
                Log.e("SearchFragment", "Error fetching meals by category", e)
                withContext(Dispatchers.Main) {
                    toggleEmptyView(true)
                }
            }
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
