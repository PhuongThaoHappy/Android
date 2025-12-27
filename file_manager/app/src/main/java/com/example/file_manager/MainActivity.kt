package com.example.file_manager

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.view.*
import android.widget.*
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import java.io.File

class MainActivity : AppCompatActivity() {

    private lateinit var listView: ListView
    private lateinit var adapter: FileAdapter
    private var currentDir: File = Environment.getExternalStorageDirectory()
    private var selectedFile: File? = null

    // =================== ON CREATE ===================
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listView = findViewById(R.id.listViewFiles)
        registerForContextMenu(listView)

        if (!hasStoragePermission()) {
            requestStoragePermission()
        } else {
            loadFiles(currentDir)
        }

        // Click file / folder
        listView.setOnItemClickListener { _, _, position, _ ->
            val file = adapter.getItem(position) ?: return@setOnItemClickListener
            if (file.isDirectory) {
                currentDir = file
                loadFiles(file)
            } else {
                openFile(file)
            }
        }

        // Back quay về thư mục cha
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (currentDir.parentFile != null) {
                    currentDir = currentDir.parentFile!!
                    loadFiles(currentDir)
                } else finish()
            }
        })
    }

    // =================== PERMISSION ===================
    private fun hasStoragePermission(): Boolean =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R)
            Environment.isExternalStorageManager()
        else
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED

    private fun requestStoragePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
            intent.data = "package:$packageName".toUri()
            startActivity(intent)
        } else {
            permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
    }

    private val permissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (it) loadFiles(currentDir)
            else Toast.makeText(this, "Chưa cấp quyền", Toast.LENGTH_LONG).show()
        }

    // =================== LOAD FILE ===================
    private fun loadFiles(dir: File) {
        val files = dir.listFiles()?.sortedWith(
            compareBy<File> { !it.isDirectory }.thenBy { it.name.lowercase() }
        ) ?: emptyList()

        adapter = FileAdapter(this, files)
        listView.adapter = adapter
    }

    // =================== OPEN FILE ===================
    private fun openFile(file: File) {
        when {
            file.name.endsWith(".txt", true) -> {
                val intent = Intent(this, TextViewerActivity::class.java)
                intent.putExtra("path", file.absolutePath)
                startActivity(intent)
            }

            file.name.endsWith(".jpg", true)
                    || file.name.endsWith(".png", true)
                    || file.name.endsWith(".bmp", true) -> {

                val uri = FileProvider.getUriForFile(
                    this,
                    "$packageName.provider",
                    file
                )

                val intent = Intent(Intent.ACTION_VIEW).apply {
                    setDataAndType(uri, "image/*")
                    addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                }

                startActivity(intent)
            }
        }
    }

    // =================== CONTEXT MENU ===================
    override fun onCreateContextMenu(
        menu: ContextMenu,
        v: View,
        menuInfo: ContextMenu.ContextMenuInfo
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        selectedFile = adapter.getItem(info.position)

        menu.add("Đổi tên")
        menu.add("Xóa")
        if (selectedFile?.isFile == true) {
            menu.add("Sao chép")
        }
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when (item.title) {
            "Đổi tên" -> renameFile()
            "Xóa" -> deleteFile()
            "Sao chép" -> copyFile()
        }
        return true
    }

    // =================== FILE ACTIONS ===================
    private fun renameFile() {
        val input = EditText(this)
        input.setText(selectedFile?.name)

        AlertDialog.Builder(this)
            .setTitle("Đổi tên")
            .setView(input)
            .setPositiveButton("OK") { _, _ ->
                val newFile = File(selectedFile!!.parent, input.text.toString())
                selectedFile!!.renameTo(newFile)
                loadFiles(currentDir)
            }
            .setNegativeButton("Hủy", null)
            .show()
    }

    private fun deleteFile() {
        AlertDialog.Builder(this)
            .setTitle("Xóa")
            .setMessage("Bạn chắc chắn muốn xóa?")
            .setPositiveButton("OK") { _, _ ->
                selectedFile!!.deleteRecursively()
                loadFiles(currentDir)
            }
            .setNegativeButton("Hủy", null)
            .show()
    }

    private fun getAllFolders(dir: File): List<File> {
        val result = mutableListOf<File>()

        dir.listFiles()?.forEach {
            if (it.isDirectory && it.canWrite()) {
                result.add(it)
            }
        }
        return result
    }
    private fun copyFile() {
        if (selectedFile == null) return

        val rootDir = Environment.getExternalStorageDirectory()
        val folders = getAllFolders(rootDir)

        if (folders.isEmpty()) {
            Toast.makeText(this, "Không tìm thấy thư mục đích", Toast.LENGTH_SHORT).show()
            return
        }

        val folderNames = folders.map { it.absolutePath }.toTypedArray()

        AlertDialog.Builder(this)
            .setTitle("Chọn thư mục đích")
            .setItems(folderNames) { _, which ->
                val targetDir = folders[which]
                val destFile = File(targetDir, selectedFile!!.name)

                try {
                    selectedFile!!.copyTo(destFile, overwrite = true)
                    Toast.makeText(
                        this,
                        "Đã sao chép vào:\n${targetDir.absolutePath}",
                        Toast.LENGTH_LONG
                    ).show()
                } catch (_: Exception) {
                    Toast.makeText(this, "Lỗi sao chép", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Hủy", null)
            .show()
    }

    // =================== OPTION MENU ===================
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menu.add("Tạo thư mục")
        menu.add("Tạo file TXT")
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.title) {
            "Tạo thư mục" -> createFolder()
            "Tạo file TXT" -> createTextFile()
        }
        return true
    }

    private fun createFolder() {
        val input = EditText(this)
        AlertDialog.Builder(this)
            .setTitle("Tên thư mục")
            .setView(input)
            .setPositiveButton("OK") { _, _ ->
                File(currentDir, input.text.toString()).mkdir()
                loadFiles(currentDir)
            }
            .show()
    }

    private fun createTextFile() {
        val input = EditText(this)

        AlertDialog.Builder(this)
            .setTitle("Tên file (không cần .txt)")
            .setView(input)
            .setPositiveButton("OK") { _, _ ->

                val fileName = input.text.toString().trim()

                if (fileName.isEmpty()) {
                    Toast.makeText(this, "Tên file không được để trống", Toast.LENGTH_SHORT).show()
                    return@setPositiveButton
                }

                try {
                    val file = File(currentDir, "$fileName.txt")

                    if (file.exists()) {
                        Toast.makeText(this, "File đã tồn tại", Toast.LENGTH_SHORT).show()
                        return@setPositiveButton
                    }

                    file.createNewFile()

                    Toast.makeText(this, "Đã tạo file TXT", Toast.LENGTH_SHORT).show()
                    loadFiles(currentDir)

                } catch (e: Exception) {
                    Toast.makeText(this, "Không thể tạo file", Toast.LENGTH_LONG).show()
                    e.printStackTrace()
                }
            }
            .setNegativeButton("Hủy", null)
            .show()
    }
}
