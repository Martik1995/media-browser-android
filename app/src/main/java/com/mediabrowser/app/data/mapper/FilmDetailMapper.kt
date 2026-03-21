package com.mediabrowser.app.data.mapper

import com.mediabrowser.app.data.dto.FilmDetailDto
import com.mediabrowser.app.domain.entity.MediaDetail

fun FilmDetailDto.toDomain(): MediaDetail {
    return MediaDetail(
        id = id.orEmpty(),
        title = title.orEmpty(),
        bannerUrl = movieBanner.orEmpty(),
        posterUrl = image.orEmpty(),
        description = description.orEmpty(),
        director = director.orEmpty(),
        producer = producer.orEmpty(),
        releaseDate = releaseDate.orEmpty(),
        runningTime = runningTime.orEmpty(),
        rtScore = rtScore.orEmpty()
    )
}