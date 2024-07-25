package com.example.masterchef.dashboard.favorite

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.masterchef.R
import com.example.masterchef.dto.fav.FavDataBase
import com.example.masterchef.dto.fav.FavouriteTable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FavAdapter(var data: List<FavouriteTable>) :
    RecyclerView.Adapter<FavAdapter.MyViewHolder>() {
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val img: ImageView = itemView.findViewById(R.id.iv_meal_fav_item)
        val name: TextView = itemView.findViewById(R.id.tv_meal_name_fav_item)
        val del: ImageView = itemView.findViewById(R.id.delete_iv_fav_item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_fav_meal, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val favDao = FavDataBase.getInstance(holder.itemView.context).favDao()

        holder.name.text = data[position].strMeal

        //todo:edit data class
        Glide.with(holder.itemView.context).load(data[position].strMealThumb).into(holder.img)
        //todo: edit favDao
        holder.del.setOnClickListener {
            GlobalScope.launch {
                favDao.delete(data[position])
                data = favDao.getAll()
                withContext(Dispatchers.Main) {
                    notifyDataSetChanged()
                }
            }
        }
    }
}