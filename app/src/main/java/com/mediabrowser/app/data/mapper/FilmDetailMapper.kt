package com.mediabrowser.app.data.mapper

import com.mediabrowser.app.data.dto.FilmDetailDto
import com.mediabrowser.app.domain.entity.MediaDetail

fun FilmDetailDto.toDomain(): MediaDetail {
    return MediaDetail(
        id = id.orEmpty(),
        title = title.orEmpty(),
        description = description.orEmpty(),
        banner = banner.orEmpty(),
        releaseDate = releaseDate.orEmpty(),
        rtScore = rtScore.orEmpty(),
        producer = producer.orEmpty(),
        director = director.orEmpty(),
        runningTime = runningTime.orEmpty(),
    )
}