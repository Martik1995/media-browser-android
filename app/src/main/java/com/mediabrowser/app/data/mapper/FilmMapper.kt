package com.mediabrowser.app.data.mapper

import com.mediabrowser.app.data.dto.FilmDto
import com.mediabrowser.app.domain.entity.Media

fun FilmDto.toDomain(): Media = Media(
    id = id.orEmpty(),
    name = title.orEmpty(),
    description = description.orEmpty(),
    imageUrl = image.orEmpty()
)