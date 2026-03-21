package com.mediabrowser.app.data.api

import com.mediabrowser.app.data.dto.FilmDetailDto
import com.mediabrowser.app.data.dto.FilmDto
import com.mediabrowser.app.data.shared.Constants
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MediaBrowserApi {

    /**
     * API for getting films.
     *
     * @param limit Number of items to load. Allowed range: 10..500.
     */
    @GET(Constants.FILMS)
    suspend fun getFilms(@Query("limit") limit: Int): List<FilmDto>

    /**
     * API for getting film details.
     *
     * @param id Film id.
     */
    @GET("${Constants.FILMS}/{id}")
    suspend fun getFilmDetails(@Path("id") id: String): FilmDetailDto
}