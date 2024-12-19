package com.example.recyclearn.data

data class VideoModel(
    val documentId: String,
    val title: String,
    val thumbnailUrl: String,
    val videoUrl: String,
    val description: String = "No description available"
)