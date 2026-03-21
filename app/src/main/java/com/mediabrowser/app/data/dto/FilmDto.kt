package com.mediabrowser.app.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FilmDto(
    @SerialName("id") val id: String? = null,
    @SerialName("title") val title: String? = null,
    @SerialName("description") val description: String? = null,
    @SerialName("image") val image: String? = null,
    @SerialName("movie_banner") val movieBanner: String? = null,
    @SerialName("release_date") val releaseDate: String? = null,
    @SerialName("rt_score") val rtScore: String? = null
)