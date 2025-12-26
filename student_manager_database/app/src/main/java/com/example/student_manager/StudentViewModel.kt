package com.example.student_manager

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class StudentViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: StudentRepository
    val students: LiveData<List<Student>>

    var selectedStudent: Student? = null

    init {
        val dao = StudentDatabase.getDatabase(application).studentDao()
        repository = StudentRepository(dao)
        students = repository.allStudents
    }

    fun addStudent(student: Student) = viewModelScope.launch {
        repository.insert(student)
    }

    fun updateStudent(student: Student) = viewModelScope.launch {
        repository.update(student)
    }

    fun deleteStudent(student: Student) = viewModelScope.launch {
        repository.delete(student)
    }

    fun addSampleStudents() = viewModelScope.launch {
        val sampleStudents = listOf(
            Student("SV001", "Le Van C", "0123456780", "HCM"),
            Student("SV002", "Pham Thi D", "0123456781", "Ha Noi"),
            Student("SV003", "Nguyen Van E", "0123456782", "Da Nang"),
            Student("SV004", "Tran Thi F", "0123456783", "Hai Phong"),
            Student("SV005", "Hoang Van G", "0123456784", "Can Tho"),
            Student("SV006", "Le Thi H", "0123456785", "Hue"),
            Student("SV007", "Pham Van I", "0123456786", "Vinh"),
            Student("SV008", "Nguyen Thi J", "0123456787", "Thai Binh"),
            Student("SV009", "Tran Van K", "0123456788", "Nam Dinh"),
            Student("SV010", "Hoang Thi L", "0123456789", "Quang Ninh")
        )

        sampleStudents.forEach { repository.insert(it) }
    }
}
