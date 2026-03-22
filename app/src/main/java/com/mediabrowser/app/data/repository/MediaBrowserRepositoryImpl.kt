package com.mediabrowser.app.data.repository

import com.mediabrowser.app.data.api.MediaBrowserApi
import com.mediabrowser.app.data.mapper.toDomain
import com.mediabrowser.app.domain.entity.Media
import com.mediabrowser.app.domain.entity.MediaDetail
import com.mediabrowser.app.domain.repository.MediaBrowserRepository

/**
 * Data-layer implementation of [MediaBrowserRepository].
 * Responsible for retrieving remote data and mapping it to domain models.
 */
class MediaBrowserRepositoryImpl(private val api: MediaBrowserApi) : MediaBrowserRepository {
    override suspend fun getMediaItems(limit: Int): List<Media> {
        return api.getFilms(limit)
            .map { it.toDomain() }
    }

    override suspend fun getMediaDetails(id: String): MediaDetail {
        return api.getFilmDetails(id).toDomain()
    }
}