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

class AddStudentFragment : Fragment() {
    private lateinit var binding: FragmentAddStudentBinding
    private lateinit var viewModel: StudentViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddStudentBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(requireActivity())[StudentViewModel::class.java]

        binding.btnSave.setOnClickListener {

            val mssv = binding.edtMSSV.text.toString().trim()
            val name = binding.edtName.text.toString().trim()
            val phone = binding.edtPhone.text.toString().trim()
            val address = binding.edtAddress.text.toString().trim()

            if (mssv.isEmpty() || name.isEmpty()) {
                Toast.makeText(
                    requireContext(),
                    "Vui lòng nhập đầy đủ MSSV và Họ tên",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            viewModel.addStudent(Student(mssv, name, phone, address))

            Toast.makeText(requireContext(), "Đã thêm sinh viên", Toast.LENGTH_SHORT).show()

            findNavController().navigateUp()
        }

        return binding.root
    }
}
