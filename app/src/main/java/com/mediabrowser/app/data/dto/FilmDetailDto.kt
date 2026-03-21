package com.mediabrowser.app.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FilmDetailDto(
    @SerialName("id") val id: String? = null,
    @SerialName("title") val title: String? = null,
    @SerialName("original_title") val originalTitle: String? = null,
    @SerialName("original_title_romanised") val originalTitleRomanised: String? = null,
    @SerialName("image") val image: String? = null,
    @SerialName("movie_banner") val movieBanner: String? = null,
    @SerialName("description") val description: String? = null,
    @SerialName("director") val director: String? = null,
    @SerialName("producer") val producer: String? = null,
    @SerialName("release_date") val releaseDate: String? = null,
    @SerialName("running_time") val runningTime: String? = null,
    @SerialName("rt_score") val rtScore: String? = null,
    @SerialName("people") val people: List<String?>? = null,
    @SerialName("species") val species: List<String?>? = null,
    @SerialName("locations") val locations: List<String?>? = null,
    @SerialName("vehicles") val vehicles: List<String?>? = null,
    @SerialName("url") val url: String? = null
)