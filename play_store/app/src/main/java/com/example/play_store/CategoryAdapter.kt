package com.example.play_store

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CategoryAdapter(private val categories: List<Category>) :
    RecyclerView.Adapter<CategoryAdapter.CategoryVH>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryVH {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_category, parent, false)
        return CategoryVH(v)
    }

    override fun onBindViewHolder(holder: CategoryVH, position: Int) {
        val category = categories[position]
        holder.title.text = category.title

        val lm = LinearLayoutManager(holder.itemView.context, LinearLayoutManager.HORIZONTAL, false)
        holder.rvApps.layoutManager = lm
        holder.rvApps.adapter = AppAdapter(category.apps)

        holder.rvApps.setHasFixedSize(true)
    }

    override fun getItemCount(): Int = categories.size

    class CategoryVH(v: View) : RecyclerView.ViewHolder(v) {
        val title: TextView = v.findViewById(R.id.tvCategoryTitle)
        val rvApps: RecyclerView = v.findViewById(R.id.rvApps)
    }
}
