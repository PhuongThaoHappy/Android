package com.example.student_manager

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
class StudentViewModel : ViewModel() {
    private val _students = MutableLiveData<MutableList<Student>>()
    val students: MutableLiveData<MutableList<Student>> get() = _students
    var selectedStudent: Student? = null

    init {
        _students.value = mutableListOf(
            Student("SV001", "Nguyen Van A", "0123456789", "HCM"),
            Student("SV002", "Tran Thi B", "0987654321", "Ha Noi")
        )
    }
    fun addStudent(student: Student) {
        val newList = _students.value?.toMutableList() ?: mutableListOf()
        newList.add(student)
        _students.value = newList
    }
    fun updateStudent(student: Student) {
        val newList = _students.value?.toMutableList() ?: return
        val index = newList.indexOfFirst { it.mssv == student.mssv }
        if (index != -1) {
            newList[index] = student
            _students.value = newList
        }
    }
}
