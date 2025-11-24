package com.example.play_store

data class AppItem(
    val id: String,
    val name: String,
    val sizeText: String,
    val rating: Float,
    val iconPath: String? = null
)

data class Category(
    val id: String,
    val title: String,
    val apps: List<AppItem>
)
