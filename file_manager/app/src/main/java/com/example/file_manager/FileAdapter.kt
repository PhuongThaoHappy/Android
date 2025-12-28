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
    files: MutableList<File>
) : ArrayAdapter<File>(context, R.layout.file_item, files) {

    // Mutable list quản lý file
    private val fileList: MutableList<File> = files

    override fun getCount(): Int = fileList.size
    override fun getItem(position: Int): File? = fileList.getOrNull(position)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.file_item, parent, false)

        val tvName = view.findViewById<TextView>(R.id.tvFileName)
        val tvType = view.findViewById<TextView>(R.id.tvFileType)

        val file = fileList[position]
        tvName.text = file.name
        tvType.text = if (file.isDirectory) "Folder" else "File"

        return view
    }

    // =================== METHODS ===================

    // Thêm file mới vào danh sách và refresh adapter
    fun addFile(file: File) {
        fileList.add(file)
        notifyDataSetChanged()
    }

    // Cập nhật toàn bộ danh sách file và refresh adapter
    fun setFiles(files: List<File>) {
        fileList.clear()
        fileList.addAll(files)
        notifyDataSetChanged()
    }

    // Xóa file khỏi danh sách và refresh adapter
    fun removeFile(file: File) {
        fileList.remove(file)
        notifyDataSetChanged()
    }

    // Lấy vị trí của file trong danh sách
    fun getPositionByFile(file: File): Int {
        return fileList.indexOfFirst { it.absolutePath == file.absolutePath }
    }
}