package com.example.masterchef.dashboard.plan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.lifecycle.lifecycleScope
import com.example.masterchef.R
import com.example.masterchef.databinding.DialogPlanBinding
import com.example.masterchef.dto.plan.PlanDAO
import com.example.masterchef.dto.plan.PlanDatabase
import com.example.masterchef.dto.plan.PlanTable
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.launch


class AddPlanDiaogFragment(private val mealName: String) : BottomSheetDialogFragment() {
    private lateinit var binding: DialogPlanBinding
    private lateinit var planDAO: PlanDAO
    private lateinit var autoCompleteType: AutoCompleteTextView
    private lateinit var autoCompleteDays: AutoCompleteTextView
    private var selectedDay: String = "sunday"
    private var selectedType: String = "breakfast"


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogPlanBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        autoCompleteType = view.findViewById(R.id.autoCompleteTextView_plan)
        autoCompleteDays = view.findViewById(R.id.autoCompleteTextView_day)
        planDAO = PlanDatabase.getInstance(requireContext()).planDAO()

        val days =
            listOf("sunday", "monday", "tuesday", "wednesday", "thursday", "friday", "saturday")

        val arrayAdapterDay = ArrayAdapter(
            requireContext(),
            R.layout.item_country,
            days
        )
        autoCompleteDays.setAdapter(arrayAdapterDay)

        val types = listOf("breakfast", "lunch", "dinner")

        val arrayAdapterType = ArrayAdapter(
            requireContext(),
            R.layout.item_country,
            types
        )
        autoCompleteType.setAdapter(arrayAdapterType)

        binding.btnSaveDay.setOnClickListener {
            lifecycleScope.launch {
                addMealToPan()
            }
            dismiss()
        }
        autoCompleteType.setOnItemClickListener { parent, _, position, _ ->
            selectedType = parent.getItemAtPosition(position).toString()
        }
        autoCompleteDays.setOnItemClickListener { parent, _, position, _ ->
            selectedDay = parent.getItemAtPosition(position).toString()
        }
    }

    private suspend fun addMealToPan(): List<Long> {
        val new = PlanTable(
            selectedDay,
            selectedType,
            mealName
        )
        return planDAO.insert(new)
    }
}