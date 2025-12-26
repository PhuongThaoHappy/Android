package com.example.student_manager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.student_manager.databinding.FragmentStudentDetailBinding

class StudentDetailFragment : Fragment() {

    private var _binding: FragmentStudentDetailBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: StudentViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStudentDetailBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity())[StudentViewModel::class.java]

        // Hiển thị thông tin sinh viên
        val student = viewModel.selectedStudent
        student?.let {
            binding.edtMSSV.setText(it.mssv)
            binding.edtMSSV.isEnabled = false // không cho sửa MSSV
            binding.edtName.setText(it.name)
            binding.edtPhone.setText(it.phone)
            binding.edtAddress.setText(it.address)
        }

        // Xử lý nút Cập nhật
        binding.btnUpdate.setOnClickListener {
            updateStudent()
        }

        return binding.root
    }

    private fun updateStudent() {
        val mssv = binding.edtMSSV.text.toString().trim()
        val name = binding.edtName.text.toString().trim()
        val phone = binding.edtPhone.text.toString().trim()
        val address = binding.edtAddress.text.toString().trim()

        if (name.isEmpty()) {
            Toast.makeText(requireContext(), "Họ tên không được để trống", Toast.LENGTH_SHORT).show()
            return
        }

        val updatedStudent = Student(
            mssv = mssv,
            name = name,
            phone = phone,
            address = address
        )

        // Cập nhật dữ liệu trong Room
        viewModel.updateStudent(updatedStudent)

        Toast.makeText(requireContext(), "Đã cập nhật sinh viên", Toast.LENGTH_SHORT).show()

        findNavController().popBackStack()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
