package com.example.student_manager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController

class StudentDetailFragment : Fragment() {
    private lateinit var edtMSSV: EditText
    private lateinit var edtName: EditText
    private lateinit var edtPhone: EditText
    private lateinit var edtAddress: EditText
    private lateinit var btnUpdate: Button
    private val viewModel: StudentViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(R.layout.fragment_student_detail, container, false)

        edtMSSV = view.findViewById(R.id.edtMSSV)
        edtName = view.findViewById(R.id.edtName)
        edtPhone = view.findViewById(R.id.edtPhone)
        edtAddress = view.findViewById(R.id.edtAddress)
        btnUpdate = view.findViewById(R.id.btnUpdate)

        viewModel.selectedStudent?.let {
            edtMSSV.setText(it.mssv)
            edtName.setText(it.name)
            edtPhone.setText(it.phone)
            edtAddress.setText(it.address)
        }

        btnUpdate.setOnClickListener {
            updateStudent()
        }

        return view
    }

    private fun updateStudent() {

        if (edtName.text.isNullOrBlank()) {
            Toast.makeText(
                requireContext(),
                "Họ tên không được để trống",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        val updatedStudent = Student(
            mssv = edtMSSV.text.toString(),
            name = edtName.text.toString(),
            phone = edtPhone.text.toString(),
            address = edtAddress.text.toString()
        )

        viewModel.updateStudent(updatedStudent)

        Toast.makeText(requireContext(), "Đã cập nhật sinh viên", Toast.LENGTH_SHORT).show()

        findNavController().popBackStack()
    }
}
