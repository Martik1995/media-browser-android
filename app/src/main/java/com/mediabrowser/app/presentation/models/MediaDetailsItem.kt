package com.mediabrowser.app.presentation.models

data class MediaDetailsItem(
    val imageUrl: String,
    val title: String,
    val description: String,
    val releaseDate: String,
    val score: String,
    val producer: String,
    val director: String,
    val delay: String
)