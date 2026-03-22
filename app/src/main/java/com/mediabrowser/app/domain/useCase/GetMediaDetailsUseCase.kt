package com.mediabrowser.app.domain.useCase

import com.mediabrowser.app.domain.entity.MediaDetail
import com.mediabrowser.app.domain.repository.MediaBrowserRepository

class GetMediaDetailsUseCase(
    private val repository: MediaBrowserRepository
) {
    suspend operator fun invoke(id: String): MediaDetail {
        return repository.getMediaDetails(id)
    }
}