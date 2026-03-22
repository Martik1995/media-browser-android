package com.mediabrowser.app.data.api

import com.mediabrowser.app.data.dto.FilmDetailDto
import com.mediabrowser.app.data.dto.FilmDto
import com.mediabrowser.app.data.shared.EndPoints
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MediaBrowserApi {

    /**
     * API for getting films.
     *
     * @param limit Number of items to load. Allowed range: 10..500.
     */
    @GET(EndPoints.FILMS_API)
    suspend fun getFilms(@Query("limit") limit: Int): List<FilmDto>

    /**
     * API for getting film details.
     *
     * @param id Film id.
     */
    @GET("${EndPoints.FILMS_API}/{id}")
    suspend fun getFilmDetails(@Path("id") id: String): FilmDetailDto
}