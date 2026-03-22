package com.mediabrowser.app.domain.repository

import com.mediabrowser.app.domain.entity.Media
import com.mediabrowser.app.domain.entity.MediaDetail

/**
 * Domain contract for accessing media data.
 * Hides data source and implementation details from upper layers.
 */
interface MediaBrowserRepository {
    suspend fun getMediaItems(limit: Int): List<Media>
    suspend fun getMediaDetails(id: String): MediaDetail
}