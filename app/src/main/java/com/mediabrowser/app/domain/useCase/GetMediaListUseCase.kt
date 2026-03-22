package com.mediabrowser.app.domain.useCase

import com.mediabrowser.app.domain.entity.Media
import com.mediabrowser.app.domain.repository.MediaBrowserRepository

/**
 * Use case represents a single domain action: loading media list.
 * It keeps presentation layer independent from repository call details
 * and is a dedicated place for business rules such as default values
 * and input validation.
 */
class GetMediaListUseCase(
    private val repository: MediaBrowserRepository
) {
    companion object {
        private const val DEF_LIMIT = 50
        private const val MIN_LIMIT = 10
        private const val MAX_LIMIT = 500
    }

    suspend operator fun invoke(limit: Int = DEF_LIMIT): List<Media> {
        return repository.getMediaItems(limit.coerceIn(MIN_LIMIT, MAX_LIMIT))
    }
}