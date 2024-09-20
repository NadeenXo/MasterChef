package com.example.masterchef.dashboard.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.masterchef.R
import com.example.masterchef.dto.fav.FavDataBase
import com.example.masterchef.dto.fav.FavouriteTable

class FavouriteFragment : Fragment(), FavMealListener {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: FavMealsViewModel
    private lateinit var adapter: FavAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_favourite, container, false)
        recyclerView = view.findViewById(R.id.rv_fav)
        recyclerView.layoutManager = LinearLayoutManager(requireActivity())
        adapter = FavAdapter(listOf(), this)
        recyclerView.adapter = adapter

//        val favDao = FavDataBase.getInstance(requireContext()).favDao()

        setupViewModel()
        viewModel.getData()

        viewModel.meals.observe(viewLifecycleOwner) { newData ->
            adapter.updateData(newData)
        }

//in viewmodel
//        lifecycleScope.launch(Dispatchers.IO) {
//            try {
//                val favMeals = favDao.getAll()
//                withContext(Dispatchers.Main) {
//                    recyclerView.layoutManager = LinearLayoutManager(requireActivity())
//                    recyclerView.adapter = FavAdapter(favMeals)
//                }
//
//            } catch (e: Exception) {
//                Log.e("HomeFragment", "Exception: ${e.message}")
//            }
//        }
        return view
    }

    private fun setupViewModel() {
        val favDao = FavDataBase.getInstance(requireContext()).favDao()
        val factory = FavFactory(favDao)
        viewModel = ViewModelProvider(this, factory).get(FavMealsViewModel::class.java)
    }

    override fun onClickDeleteMeal(fav: FavouriteTable) {
        viewModel.removeFavMeal(fav)
    }
}

interface FavMealListener {
    fun onClickDeleteMeal(fav: FavouriteTable)

}
