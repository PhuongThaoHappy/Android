package com.example.play_store

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import java.io.File

class AppAdapter(private val items: List<AppItem>) :
    RecyclerView.Adapter<AppAdapter.AppVH>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppVH {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_app, parent, false)
        return AppVH(v)
    }

    override fun onBindViewHolder(holder: AppVH, position: Int) {
        val item = items[position]
        holder.name.text = item.name
        val ctx = holder.itemView.context
        if (!item.iconPath.isNullOrEmpty()) {
            val f = File(item.iconPath)
            if (f.exists()) {
                Glide.with(ctx).load(f).into(holder.icon)
            }
            else {
                Glide.with(ctx).load(item.iconPath).into(holder.icon)
            }
        }
        else {
            Glide.with(ctx).clear(holder.icon)
            holder.icon.setImageResource(android.R.drawable.sym_def_app_icon)
        }
        holder.itemView.setOnClickListener {}
    }

    override fun getItemCount(): Int = items.size

    class AppVH(v: View) : RecyclerView.ViewHolder(v) {
        val icon: ImageView = v.findViewById(R.id.imgApp)
        val name: TextView = v.findViewById(R.id.tvAppName)
    }
}
