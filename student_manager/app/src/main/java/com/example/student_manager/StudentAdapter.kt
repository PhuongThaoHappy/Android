package com.example.student_manager

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.view.LayoutInflater

class StudentAdapter(
    private val context: Activity,
    private val list: ArrayList<Student>
) : ArrayAdapter<Student>(context, R.layout.student_item, list) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val rowView = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.student_item, parent, false)

        val tvName = rowView.findViewById<TextView>(R.id.tvName)
        val tvMSSV = rowView.findViewById<TextView>(R.id.tvMSSV)
        val btnDelete = rowView.findViewById<ImageView>(R.id.btnDelete)

        val student = list[position]

        tvName.text = student.name
        tvMSSV.text = student.mssv

        btnDelete.setOnClickListener {
            list.removeAt(position)
            notifyDataSetChanged()
        }

        return rowView
    }
}
