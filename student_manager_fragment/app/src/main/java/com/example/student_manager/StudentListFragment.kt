package com.example.student_manager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController

class StudentListFragment : Fragment() {
    private lateinit var listView: ListView
    private lateinit var adapter: StudentAdapter
    private lateinit var btnAdd: Button
    private val viewModel: StudentViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_student_list, container, false)

        listView = view.findViewById(R.id.listViewStudent)
        btnAdd = view.findViewById(R.id.btnAdd)

        adapter = StudentAdapter(
            requireContext(),
            mutableListOf()
        ) { student ->
            viewModel.selectedStudent = student
            findNavController().navigate(R.id.action_studentListFragment_to_studentDetailFragment)
        }

        listView.adapter = adapter

        observeViewModel()

        btnAdd.setOnClickListener {
            findNavController().navigate(R.id.action_studentListFragment_to_addStudentFragment)
        }
        return view
    }

    private fun observeViewModel() {
        viewModel.students.observe(viewLifecycleOwner) { list ->
            adapter.clear()
            adapter.addAll(list)
            adapter.notifyDataSetChanged()
        }
    }
}
