package com.example.play_store

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private val sampleLocalImagePath = "/mnt/data/3791421b-7e5a-4ab8-8182-5933f9732a62.png"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        val rvCategories = findViewById<RecyclerView>(R.id.rvCategories)
        rvCategories.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        val categories = generateDemoCategories()
        val adapter = CategoryAdapter(categories)
        rvCategories.adapter = adapter
        rvCategories.setHasFixedSize(true)
    }

    private fun generateDemoCategories(): List<Category> {
        val apps1 = mutableListOf<AppItem>()
        val apps2 = mutableListOf<AppItem>()
        val apps3 = mutableListOf<AppItem>()

        for (i in 1..8) {
            apps1.add(
                AppItem(
                    id = "m1-$i",
                    name = "Mech Assemble $i",
                    sizeText = "${200 + i} MB",
                    rating = 4.2f,
                    iconPath = sampleLocalImagePath
                )
            )
        }
        for (i in 1..6) {
            apps2.add(
                AppItem(
                    id = "m2-$i",
                    name = "MU Hồng Hoả $i",
                    sizeText = "${150 + i} MB",
                    rating = 4.8f,
                    iconPath = sampleLocalImagePath
                )
            )
        }
        for (i in 1..7) {
            apps3.add(
                AppItem(
                    id = "m3-$i",
                    name = "War Inc $i",
                    sizeText = "${100 + i} MB",
                    rating = 4.6f,
                    iconPath = sampleLocalImagePath
                )
            )
        }

        return listOf(
            Category(id = "c1", title = "Suggested for you", apps = apps1),
            Category(id = "c2", title = "Recommended for you", apps = apps2),
            Category(id = "c3", title = "Top strategy", apps = apps3)
        )
    }
}
