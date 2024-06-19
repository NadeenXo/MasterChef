package com.example.masterchef

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class MealsAdapter(val data: ArrayList<MealsCategories>) :
    RecyclerView.Adapter<MealsAdapter.MyViewHolder>() {
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // val img: ImageView = itemView.findViewById(R.id.imageView)
        val name: TextView = itemView.findViewById(R.id.category_tv)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_meals, parent, false)
        )

    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        //Glide.with(holder.itemView.context).load(data[position].meals).into(holder.img)

        holder.name.text = data[position].strCategory

        holder.itemView.setOnClickListener {
            Toast.makeText(holder.itemView.context, holder.name.text, Toast.LENGTH_SHORT).show()
        }
    }


}