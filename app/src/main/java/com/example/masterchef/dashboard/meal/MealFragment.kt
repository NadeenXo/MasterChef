package com.example.masterchef.dashboard.meal

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.masterchef.R
import com.example.masterchef.dashboard.meal.view.MealAdapter
import com.example.masterchef.dashboard.meal.view.MealListener
import com.example.masterchef.network.APIClient
import com.example.masterchef.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MealFragment : Fragment() ,MealListener{
    //todo: call endpoint


    private lateinit var rvMeals: RecyclerView
    private lateinit var service: ApiService
   private lateinit var categoryName: String

    companion object {
        private const val ARG_CATEGORY_NAME = "category_name"
        fun newInstance(name: String): MealFragment {
            val fragment = MealFragment()
            val args = Bundle()
            args.putString(ARG_CATEGORY_NAME, name)
            fragment.arguments = args
            return fragment
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_meal, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvMeals = view.findViewById(R.id.rv_meal)
        categoryName= arguments?.getString(ARG_CATEGORY_NAME).toString()

        service = APIClient.getInstance()
        fetchMealsByCategory(categoryName)

    }
    private fun navigateToMealDetailsFragment() {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.nav_host_fragment_dashboard, MealDetailsFragment())
            .addToBackStack("MealDetailsFragment")
            .commit()
    }


    private fun fetchMealsByCategory(categoryName: String) {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val response = service.getMealsByCategory(categoryName)
                if (response.isSuccessful) {
                    val meals = response.body()?.meals
                    withContext(Dispatchers.Main) {
                        rvMeals.layoutManager = LinearLayoutManager(context)
//                        rvMeals.adapter = MealAdapter(meals ?: emptyList(), this@MealFragment)
                        rvMeals.adapter = MealAdapter(meals ?: emptyList(),this@MealFragment)
                    }
                } else {
                    // Handle the error
                }
            } catch (e: Exception) {
                // Handle the exception
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