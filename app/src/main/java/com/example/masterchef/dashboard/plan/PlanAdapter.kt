package com.example.masterchef.dashboard.plan

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.masterchef.R
import com.example.masterchef.dto.fav.FavDataBase
import com.example.masterchef.dto.plan.PlanTable

class PlanAdapter(var data: List<PlanTable>) :
    RecyclerView.Adapter<PlanAdapter.MyViewHolder>() {
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val day: TextView  = itemView.findViewById(R.id.tv_day)
        val name: TextView = itemView.findViewById(R.id.tv_meal_name_plan)
        val type: TextView = itemView.findViewById(R.id.tv_type)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_plan, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val favDao = FavDataBase.getInstance(holder.itemView.context).favDao()

        holder.name.text = data[position].mealName
        holder.type.text = data[position].type
        holder.day.text = data[position].dayWeek

//        holder.del.setOnClickListener {
//            GlobalScope.launch {
//                favDao.delete(data[position])
//                data = favDao.getAll()
//                withContext(Dispatchers.Main) {
//                    notifyDataSetChanged()
//                }
//            }
//        }
    }
}