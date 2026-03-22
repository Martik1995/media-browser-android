package com.mediabrowser.app.di

import com.mediabrowser.app.presentation.mediaList.MediaListViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val PresentationModule = module {
    viewModelOf(::MediaListViewModel)
}