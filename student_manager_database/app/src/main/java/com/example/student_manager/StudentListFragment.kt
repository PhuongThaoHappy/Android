package com.example.student_manager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.student_manager.databinding.FragmentStudentListBinding

class StudentListFragment : Fragment() {

    private var _binding: FragmentStudentListBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: StudentAdapter
    private lateinit var viewModel: StudentViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStudentListBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity())[StudentViewModel::class.java]

        // Khởi tạo adapter với callback Xóa
        adapter = StudentAdapter(
            requireContext(),
            mutableListOf(),
            onItemClick = { student ->
                viewModel.selectedStudent = student
                findNavController().navigate(R.id.action_studentListFragment_to_studentDetailFragment)
            },
            onDeleteClick = { student ->
                viewModel.deleteStudent(student)
            }
        )

        binding.listViewStudent.adapter = adapter

        // Quan sát LiveData từ Room
        viewModel.students.observe(viewLifecycleOwner) { list ->
            adapter.updateList(list)
        }

        // Nút Thêm sinh viên
        binding.btnAdd.setOnClickListener {
            findNavController().navigate(R.id.action_studentListFragment_to_addStudentFragment)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
