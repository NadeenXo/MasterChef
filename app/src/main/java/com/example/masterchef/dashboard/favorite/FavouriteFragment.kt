package com.example.masterchef.dashboard.favorite

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
import com.example.masterchef.dto.FavDataBase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FavouriteFragment : Fragment() {
    lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_favourite, container, false)
        recyclerView = view.findViewById(R.id.rv_fav)

        val favDao = FavDataBase.getInstance(requireContext()).favDao()

        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val favMeals = favDao.getAll()
                withContext(Dispatchers.Main) {
                    recyclerView.layoutManager = LinearLayoutManager(requireActivity())
                    recyclerView.adapter = FavAdapter(favMeals)
                }

            } catch (e: Exception) {
                Log.e("HomeFragment", "Exception: ${e.message}")
            }
        }
        return view
    }
}