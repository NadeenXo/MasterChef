package com.example.masterchef.dashboard.meal.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.masterchef.R

class IngredientsAdapter(private val ingredientsWithMeasures: List<Pair<String, String>>) :
    RecyclerView.Adapter<IngredientsAdapter.IngredientViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_ingredient, parent, false)
        return IngredientViewHolder(view)
    }

    override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {
        holder.bind(ingredientsWithMeasures[position])
    }

    override fun getItemCount(): Int = ingredientsWithMeasures.size

    class IngredientViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ingredientTextView: TextView = itemView.findViewById(R.id.tv_ingredients)

        fun bind(ingredientWithMeasure: Pair<String, String>) {
            ingredientTextView.text = "${ingredientWithMeasure.first}: ${ingredientWithMeasure.second}"
        }
    }
}
