package com.example.student_manager

import java.io.Serializable
data class Student(
    val mssv: String,
    val name: String,
    val phone: String,
    val address: String,
) : Serializable
