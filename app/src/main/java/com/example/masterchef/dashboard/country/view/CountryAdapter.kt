package com.example.masterchef.dashboard.country.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.masterchef.R
import com.example.masterchef.dashboard.country.model.MealAreaStr

class CountryAdapter(private val data: List<MealAreaStr>, private val listener: CountryListener) :
    RecyclerView.Adapter<CountryAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.tv_country_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_country, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val country = data[position]
        holder.name.text = country.strArea
        holder.itemView.setOnClickListener {
            listener.onClick(country)
        }
    }
}

interface CountryListener {
    fun onClick(country: MealAreaStr)
}
