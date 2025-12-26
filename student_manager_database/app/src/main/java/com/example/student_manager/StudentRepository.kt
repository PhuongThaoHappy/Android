package com.example.student_manager

import androidx.lifecycle.LiveData

class StudentRepository(private val dao: StudentDao) {

    val allStudents: LiveData<List<Student>> = dao.getAllStudents()

    suspend fun insert(student: Student) = dao.insertStudent(student)
    suspend fun update(student: Student) = dao.updateStudent(student)
    suspend fun delete(student: Student) = dao.deleteStudent(student)
}
