package com.mediabrowser.app.domain.repository

import com.mediabrowser.app.domain.entity.Media
import com.mediabrowser.app.domain.entity.MediaDetail

interface MediaBrowserRepository {

    /**
     * @param limit Number of items to load. Min 10, max 500.
     */
    suspend fun getMediaItems(limit: Int): List<Media>

    /**
     * @param id Film id.
     */
    suspend fun getMediaDetails(id: String): MediaDetail
}