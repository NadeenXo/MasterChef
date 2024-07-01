package com.example.masterchef.dashboard.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.masterchef.R
import com.example.masterchef.dashboard.Meals
import com.example.masterchef.dto.FavDataBase
import com.example.masterchef.dto.fav
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class RandomMealsAdapter(val data: List<Meals>) :
    RecyclerView.Adapter<RandomMealsAdapter.MyViewHolder>() {
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val id: TextView = itemView.findViewById(R.id.id_tv)
        val str: TextView = itemView.findViewById(R.id.str_tv)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_random_meals, parent, false)
        )

    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.id.text = data[position].idMeal
        holder.str.text = data[position].strMeal
        val favDao = FavDataBase.getInstance(holder.itemView.context).favDao()


        val newFav = fav(idMeal = holder.id.text.toString(), strMeal = holder.str.text.toString())


        holder.itemView.setOnClickListener {
            GlobalScope.launch {  favDao.insert(newFav) }
//            Toast.makeText(holder.itemView.context, holder.name.text, Toast.LENGTH_SHORT).show()
        }
    }

}