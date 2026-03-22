package com.mediabrowser.app.presentation.mapper

import com.mediabrowser.app.domain.entity.MediaDetail
import com.mediabrowser.app.presentation.models.MediaDetailsItem

fun MediaDetail.toItem() = MediaDetailsItem(
    imageUrl = banner,
    title = title,
    description = description,
    releaseDate = releaseDate,
    score = rtScore,
    producer = producer,
    director = director,
    delay = runningTime
)