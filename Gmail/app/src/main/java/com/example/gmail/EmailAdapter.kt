package com.example.gmail

import android.annotation.SuppressLint
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlin.random.Random

class EmailAdapter(private val items: List<Email>, private val onClick: (Email) -> Unit) :
    RecyclerView.Adapter<EmailAdapter.EmailVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmailVH {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_email, parent, false)
        return EmailVH(v)
    }

    override fun onBindViewHolder(holder: EmailVH, position: Int) {
        val email = items[position]
        holder.bind(email)
        holder.itemView.setOnClickListener { onClick(email) }
    }

    override fun getItemCount(): Int = items.size

    class EmailVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvAvatar: TextView = itemView.findViewById(R.id.tvAvatar)
        private val tvFrom: TextView = itemView.findViewById(R.id.tvFrom)
        private val tvSubject: TextView = itemView.findViewById(R.id.tvSubject)
        private val tvSnippet: TextView = itemView.findViewById(R.id.tvSnippet)
        private val tvTime: TextView = itemView.findViewById(R.id.tvTime)
        private val ivStar: ImageView = itemView.findViewById(R.id.ivStar)

        @SuppressLint("UseCompatLoadingForDrawables")
        fun bind(email: Email) {
            tvFrom.text = email.from
            tvSubject.text = email.subject
            tvSnippet.text = email.snippet
            tvTime.text = email.time

            val initial = if (email.from.isNotEmpty()) email.from.trim()[0].uppercaseChar() else '?'
            tvAvatar.text = initial.toString()

            val color = randomColor()
            val bg = tvAvatar.background
            if (bg is GradientDrawable) {
                bg.setColor(color)
            } else {
                val drawable = tvAvatar.context.getDrawable(R.drawable.circle_bg)
                if (drawable is GradientDrawable) drawable.setColor(color)
                tvAvatar.background = drawable
            }
            ivStar.alpha = 0.9f
        }

        private fun randomColor(): Int {
            val rnd = Random.Default
            val r = 150 + rnd.nextInt(100)
            val g = 150 + rnd.nextInt(100)
            val b = 150 + rnd.nextInt(100)
            return (0xFF shl 24) or (r shl 16) or (g shl 8) or b
        }
    }
}
