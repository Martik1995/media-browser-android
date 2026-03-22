package com.mediabrowser.app.di

import com.mediabrowser.app.domain.useCase.GetMediaListUseCase
import org.koin.dsl.module

val DomainModule = module {
    factory<GetMediaListUseCase> { GetMediaListUseCase(repository = get()) }
}