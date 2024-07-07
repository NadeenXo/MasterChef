package com.example.masterchef.dashboard.home.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.masterchef.R
import com.example.masterchef.dashboard.home.model.category.Category

class CategoriesAdapter(val data: List<Category>, private val listener: CategoryListener) :
    RecyclerView.Adapter<CategoriesAdapter.MyViewHolder>() {
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val img: ImageView = itemView.findViewById(R.id.iv_category)
        val name: TextView = itemView.findViewById(R.id.tv_category)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_category, parent, false)
        )

    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val category = data[position]

        holder.name.text = category.strCategory
        Glide.with(holder.itemView.context).load(category.strCategoryThumb)
            .error(R.drawable.ic_launcher_groceries_foreground).into(holder.img)


        //todo: open meals screen and show it based on the category
        holder.itemView.setOnClickListener {
//            Toast.makeText(holder.itemView.context, holder.name.text, Toast.LENGTH_SHORT).show()

            //filter meal by category
            //send name to the api to filter with category name
            listener.onClick(category)


//            GlobalScope.launch {
//                //todo: go from home to meals
//                val meals = service.getMealsByCategory(name)
//                withContext(Main) {
//                    findNavController(holder.itemView.rootView).navigate(R.id.action_homeFragment_to_searchFragment)
//
//                }
//            }
        }
    }

}

interface CategoryListener {
    fun onClick(category: Category)
}