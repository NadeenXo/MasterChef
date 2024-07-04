package com.example.masterchef.dashboard.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.masterchef.R
import com.example.masterchef.dashboard.Meals
import com.example.masterchef.dto.FavDataBase
import com.example.masterchef.dto.FavouriteTable
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class RandomMealsAdapter(val data: List<Meals>) :
    RecyclerView.Adapter<RandomMealsAdapter.MyViewHolder>() {
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val img: ImageView = itemView.findViewById(R.id.iv_meal_random)
        val name: TextView = itemView.findViewById(R.id.tv_meal_name_random)
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
//todo: favDao and edit adapter
        //holder.img.text = data[position].idMeal
        holder.name.text = data[position].strMeal
        Glide.with(holder.itemView.context).load(data[position].strMealThumb).into(holder.img)

        val favDao = FavDataBase.getInstance(holder.itemView.context).favDao()


        val newFav = FavouriteTable(
            idMeal = data[position].idMeal.toString(),
            strMeal = data[position].strMeal.toString(),
            strMealThumb = data[position].strMealThumb.toString()
        )

        holder.itemView.setOnClickListener {
            GlobalScope.launch { favDao.insert(newFav) }
//            Toast.makeText(holder.itemView.context, holder.name.text, Toast.LENGTH_SHORT).show()
            //todo : on click meal open details - error

            //            findNavController(holder.itemView).navigate(R.id.action_searchFragment_to_mealFragment)

        }
    }

}