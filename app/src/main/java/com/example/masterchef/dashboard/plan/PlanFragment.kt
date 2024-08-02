package com.example.masterchef.dashboard.plan

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
import com.example.masterchef.dto.plan.PlanDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PlanFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_plan, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val planDAO = PlanDatabase.getInstance(requireContext()).planDAO()
        recyclerView = view.findViewById(R.id.rv_plan)

        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val planedMeals = planDAO.getAll()
                withContext(Dispatchers.Main) {
                    recyclerView.layoutManager = LinearLayoutManager(requireActivity())
                    recyclerView.adapter = PlanAdapter(planedMeals)
                }

            } catch (e: Exception) {
                Log.e("HomeFragment", "Exception: ${e.message}")
            }
        }
    }
}