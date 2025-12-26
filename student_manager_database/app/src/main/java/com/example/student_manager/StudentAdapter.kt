package com.example.student_manager

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView

class StudentAdapter(
    context: Context,
    private var students: MutableList<Student>,
    private val onItemClick: (Student) -> Unit,
    private val onDeleteClick: (Student) -> Unit
) : ArrayAdapter<Student>(context, R.layout.student_item, students) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.student_item, parent, false)

        val tvMSSV = view.findViewById<TextView>(R.id.tvMSSV)
        val tvName = view.findViewById<TextView>(R.id.tvName)
        val btnDelete = view.findViewById<Button>(R.id.btnDelete)

        val student = students[position]

        tvMSSV.text = student.mssv
        tvName.text = student.name

        // Chọn sinh viên
        view.setOnClickListener {
            onItemClick(student)
        }

        // Xóa sinh viên
        btnDelete.setOnClickListener {
            onDeleteClick(student)
        }

        return view
    }

    // Cập nhật toàn bộ danh sách
    fun updateList(newList: List<Student>) {
        students.clear()
        students.addAll(newList)
        notifyDataSetChanged()
    }
}
