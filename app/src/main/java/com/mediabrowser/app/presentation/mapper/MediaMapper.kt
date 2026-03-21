package com.mediabrowser.app.presentation.mapper

import com.mediabrowser.app.domain.entity.Media
import com.mediabrowser.app.presentation.models.MediaItem

fun Media.toItem() = MediaItem(title = name, description = description, imageUrl = imageUrl, releaseDate = releaseDate)