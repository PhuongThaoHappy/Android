package com.example.student_manager

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "students")
data class Student(
    @PrimaryKey val mssv: String,
    val name: String,
    val phone: String,
    val address: String,
) : Serializable
