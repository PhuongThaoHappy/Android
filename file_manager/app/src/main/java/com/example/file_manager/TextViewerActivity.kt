package com.example.file_manager

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.file_manager.databinding.ActivityTextViewerBinding
import java.io.File

class TextViewerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTextViewerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTextViewerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val path = intent.getStringExtra("path") ?: return
        val file = File(path)

        binding.tvTitle.text = file.name
        binding.tvContent.text = file.readText()
    }
}
