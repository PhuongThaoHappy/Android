package com.example.student_manager

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class StudentAdapter(
    context: Context,
    private val students: MutableList<Student>,
    private val onItemClick: (Student) -> Unit
) : ArrayAdapter<Student>(context, R.layout.student_item, students) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.student_item, parent, false)

        val tvMSSV = view.findViewById<TextView>(R.id.tvMSSV)
        val tvName = view.findViewById<TextView>(R.id.tvName)

        val student = students[position]

        tvMSSV.text = student.mssv
        tvName.text = student.name

        view.setOnClickListener {
            onItemClick(student)
        }

        return view
    }

    // Thêm sinh viên
    fun addStudent(student: Student) {
        students.add(student)
        notifyDataSetChanged()
    }

    // Cập nhật sinh viên theo MSSV
    fun updateStudent(student: Student) {
        val index = students.indexOfFirst { it.mssv == student.mssv }
        if (index != -1) {
            students[index] = student
            notifyDataSetChanged()
        }
    }
}
