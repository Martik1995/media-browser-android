package com.mediabrowser.app.domain.entity

data class Media(
    val id: String,
    val name: String,
    val description: String,
    val releaseDate: String,
    val imageUrl: String
)