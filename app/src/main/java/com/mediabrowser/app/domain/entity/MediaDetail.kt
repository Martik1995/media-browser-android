package com.mediabrowser.app.domain.entity

data class MediaDetail(
    val id: String,
    val title: String,
    val description: String,
    val banner: String,
    val releaseDate: String,
    val rtScore: String,
    val producer: String,
    val director: String,
    val runningTime: String
)