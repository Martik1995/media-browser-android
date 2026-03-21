package com.mediabrowser.app.domain.entity

data class MediaDetail(
    val id: String,
    val title: String,
    val bannerUrl: String,
    val posterUrl: String,
    val description: String,
    val director: String,
    val producer: String,
    val releaseDate: String,
    val runningTime: String,
    val rtScore: String
)