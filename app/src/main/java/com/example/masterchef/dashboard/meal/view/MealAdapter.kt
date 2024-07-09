// MealAdapter.kt
package com.example.masterchef.dashboard.meal.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.masterchef.R
import com.example.masterchef.dashboard.country.CountryFragment
import com.example.masterchef.dashboard.meal.model.Meals
import com.example.masterchef.dto.FavDataBase
import com.example.masterchef.dto.FavouriteTable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MealAdapter(private val data: List<Meals>
, private val listener: MealListener
) : RecyclerView.Adapter<MealAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val img: ImageView = itemView.findViewById(R.id.iv_meal)
        val heart: ImageView = itemView.findViewById(R.id.iv_heart)
        val name: TextView = itemView.findViewById(R.id.tv_meal_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_meal, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val meal = data[position]
        val favDao = FavDataBase.getInstance(holder.itemView.context).favDao()

        // Load meal data into views
        holder.name.text = meal.strMeal
        Glide.with(holder.itemView.context).load(meal.strMealThumb).into(holder.img)

        // Check if the meal is favorite
        CoroutineScope(Dispatchers.IO).launch {
            val isFavorite = favDao.getFavById(meal.idMeal ?: "").isNotEmpty()

            // Update UI on the main thread
            withContext(Dispatchers.Main) {
                if (isFavorite) {
                    like(holder)
                } else {
                    dislike(holder)
                }
            }
        }

        // Handle favorite button click
        holder.heart.setOnClickListener {
            holder.heart.isEnabled = false // Disable the button to prevent multiple clicks

            CoroutineScope(Dispatchers.IO).launch {
                val isFavorite = favDao.getFavById(meal.idMeal).isNotEmpty()

                if (isFavorite) {
                    favDao.delete(
                        FavouriteTable(
                            meal.idMeal,
                            meal.strMeal,
                            meal.strMealThumb
                        )
                    )
                    withContext(Dispatchers.Main) {
                        dislike(holder)
                    }
                } else {
                    val newFav = FavouriteTable(
                        meal.idMeal,
                        meal.strMeal,
                        meal.strMealThumb
                    )
                    favDao.insert(newFav)
                    withContext(Dispatchers.Main) {
                        like(holder)
                    }
                }

                withContext(Dispatchers.Main) {
                    holder.heart.isEnabled = true
                }
            }
        }

        // Handle item click to navigate to meal details
        holder.itemView.setOnClickListener {
//            meal.idMeal.let { mealId ->
//                communicator.navigateToMealDetails(mealId)
//            }
            //todo : on click meal open details
            listener.onClick(meal.idMeal)
        }
    }

    private fun like(holder: MyViewHolder) {
        holder.heart.setImageDrawable(
            ContextCompat.getDrawable(
                holder.itemView.context,
                R.drawable.ic_heart_red
            )
        )
    }

    private fun dislike(holder: MyViewHolder) {
        holder.heart.setImageDrawable(
            ContextCompat.getDrawable(
                holder.itemView.context,
                R.drawable.ic_heart
            )
        )
    }
}

interface MealListener {
    fun onClick(id: String)
}


////todo: favDao and edit adapter
//        //holder.img.text = data[position].idMeal
//        holder.name.text = data[position].strMeal
//        Glide.with(holder.itemView.context).load(data[position].strMealThumb).into(holder.img)
//

//        //todo : on click meal open details - error
