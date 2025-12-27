package com.example.file_manager

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import java.io.File

class FileAdapter(
    context: Context,
    private val files: List<File>
) : ArrayAdapter<File>(context, R.layout.file_item, files) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.file_item, parent, false)

        val tvName = view.findViewById<TextView>(R.id.tvFileName)
        val tvType = view.findViewById<TextView>(R.id.tvFileType)

        val file = files[position]
        tvName.text = file.name
        tvType.text = if (file.isDirectory) "Folder" else "File"

        return view
    }
}
