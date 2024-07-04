package com.example.masterchef.dashboard.search

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.masterchef.R
import com.example.masterchef.network.APIClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class SearchFragment : Fragment() {
    lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_search, container, false)

        recyclerView = view.findViewById(R.id.rv_search)

        val service = APIClient.getInstance()
//todo: search
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val mealsResponse = service.getRandomMeals()
                if (mealsResponse.isSuccessful) {
                    val categories = mealsResponse.body()?.meals
                    withContext(Dispatchers.Main) {
                        recyclerView.layoutManager = LinearLayoutManager(requireActivity())
                        recyclerView.adapter = categories?.let { RandomMealsAdapter(it) }
                    }
                } else {
                    Log.e("HomeFragment", "Error: ${mealsResponse.code()}")
                }
            } catch (e: Exception) {
                Log.e("HomeFragment", "Exception: ${e.message}")
            }
        }
        return view
    }
}