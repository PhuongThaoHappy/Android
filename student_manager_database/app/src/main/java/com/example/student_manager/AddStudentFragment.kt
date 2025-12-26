package com.example.student_manager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.student_manager.databinding.FragmentAddStudentBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddStudentFragment : Fragment() {

    private var _binding: FragmentAddStudentBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: StudentViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddStudentBinding.inflate(inflater, container, false)

        // Khởi tạo ViewModel
        viewModel = ViewModelProvider(requireActivity())[StudentViewModel::class.java]

        // Xử lý nút Lưu
        binding.btnSave.setOnClickListener {
            saveStudent()
        }

        return binding.root
    }

    private fun saveStudent() {
        val mssv = binding.edtMSSV.text.toString().trim()
        val name = binding.edtName.text.toString().trim()
        val phone = binding.edtPhone.text.toString().trim()
        val address = binding.edtAddress.text.toString().trim()

        // Validate MSSV và Họ tên
        if (mssv.isEmpty() || name.isEmpty()) {
            Toast.makeText(requireContext(), "Vui lòng nhập đầy đủ MSSV và Họ tên", Toast.LENGTH_SHORT).show()
            return
        }

        // Tạo đối tượng Student
        val student = Student(
            mssv = mssv,
            name = name,
            phone = phone,
            address = address
        )

        // Thêm student vào database qua ViewModel (Room)
        viewModel.addStudent(student)

        // Thông báo và quay lại danh sách sinh viên
        Toast.makeText(requireContext(), "Đã thêm sinh viên", Toast.LENGTH_SHORT).show()
        findNavController().navigateUp()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
