package com.example.gmail

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gmail.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: EmailAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val emails = generateSampleEmails()
        adapter = EmailAdapter(emails) { email ->
            Toast.makeText(this, "Open: ${email.subject}", Toast.LENGTH_SHORT).show()
        }

        binding.recycler.layoutManager = LinearLayoutManager(this)
        binding.recycler.adapter = adapter
        binding.recycler.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

        binding.fabCompose.setOnClickListener {
            Toast.makeText(this, "Compose clicked", Toast.LENGTH_SHORT).show()
        }
    }

    private fun generateSampleEmails(): List<Email> {
        return listOf(
            Email("Edurila.com", "$19 Only (First 10 spots) - Bestselling...", "Are you looking to Learn Web Designing...", "12:34 PM"),
            Email("Chris Abad", "Help make Campaign Monitor better", "Let us know your thoughts! No Images...", "11:22 AM"),
            Email("Tuto.com", "8h de formation gratuite et les nouvea...", "Photoshop, SEO, Blender, CSS, WordPre...", "11:04 AM"),
            Email("support", "Société OvH : suivi de vos services", "SAS OVH - http://www.ovh.com 2 rue K...", "10:26 AM"),
            Email("Matt from Ionic", "The New Ionic Creator Is Here!", "Announcing the all-new Creator, build...", "9:10 AM"),
        )
    }
}
