package com.mediabrowser.app.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class FilmDto(
    @SerialName("id") val id: String? = null,
    @SerialName("title") val title: String? = null,
    @SerialName("description") val description: String? = null,
    @SerialName("image") val image: String? = null,
    @SerialName("release_date") val releaseDate: String? = null,
)