package com.mediabrowser.app.di

import com.mediabrowser.app.presentation.mediaDetails.MediaDetailsViewModel
import com.mediabrowser.app.presentation.mediaList.MediaListViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val PresentationModule = module {
    viewModel { MediaListViewModel(get()) }

    viewModel { params ->
        MediaDetailsViewModel(
            id = params.get(),
            getMediaDetailsUseCase = get()
        )
    }
}