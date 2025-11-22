package com.example.student_manager

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var edtMSSV: EditText
    private lateinit var edtName: EditText
    private lateinit var btnAdd: Button
    private lateinit var btnUpdate: Button
    private lateinit var listView: ListView
    private lateinit var adapter: StudentAdapter
    private val studentList = ArrayList<Student>()
    private var selectedIndex = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        edtMSSV = findViewById(R.id.edtMSSV)
        edtName = findViewById(R.id.edtName)
        btnAdd = findViewById(R.id.btnAdd)
        btnUpdate = findViewById(R.id.btnUpdate)
        listView = findViewById(R.id.listView)

        adapter = StudentAdapter(this, studentList)
        listView.adapter = adapter

        btnAdd.setOnClickListener {
            val mssv = edtMSSV.text.toString().trim()
            val name = edtName.text.toString().trim()

            if (mssv.isEmpty() || name.isEmpty()) {
                Toast.makeText(this, "Please enter all information", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            studentList.add(Student(mssv, name))
            adapter.notifyDataSetChanged()

            clearInput()
            edtMSSV.requestFocus()
        }

        btnUpdate.setOnClickListener {
            if (selectedIndex == -1) {
                Toast.makeText(this, "Choose a student to update", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val mssv = edtMSSV.text.toString().trim()
            val name = edtName.text.toString().trim()

            if (mssv.isEmpty() || name.isEmpty()) {
                Toast.makeText(this, "Please enter all information", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            studentList[selectedIndex] = Student(mssv, name)
            adapter.notifyDataSetChanged()

            clearInput()
            selectedIndex = -1
            edtMSSV.requestFocus()
        }

        listView.setOnItemClickListener { _, _, position, _ ->
            selectedIndex = position
            val st = studentList[position]

            edtMSSV.setText(st.mssv)
            edtName.setText(st.name)

            edtMSSV.requestFocus()
        }
    }
    private fun clearInput() {
        edtMSSV.setText("")
        edtName.setText("")
    }
}
