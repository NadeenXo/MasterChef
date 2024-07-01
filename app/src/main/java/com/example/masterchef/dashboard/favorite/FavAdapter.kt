package com.example.masterchef.dashboard.favorite

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.masterchef.R
import com.example.masterchef.dto.FavDataBase
import com.example.masterchef.dto.fav
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FavAdapter(var data: List<fav>) :
    RecyclerView.Adapter<FavAdapter.MyViewHolder>() {
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val id: TextView = itemView.findViewById(R.id.id_tv)
        val str: TextView = itemView.findViewById(R.id.str_tv)
        val img: ImageView = itemView.findViewById(R.id.delete_iv)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_fav_meals, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val favDao = FavDataBase.getInstance(holder.itemView.context).favDao()

        holder.id.text = data[position].idMeal
        holder.str.text = data[position].strMeal
        holder.img.setOnClickListener {
            GlobalScope.launch {
                favDao.delete(data[position])
                data = favDao.getFavs()
                withContext(Dispatchers.Main) {
                    notifyDataSetChanged()
                }
            }
        }
    }
}