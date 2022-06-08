package com.example.androidreader.dagger.module

import com.example.androidreader.domain.usecase.api.GetBookTitlesUseCase
import com.example.androidreader.domain.usecase.api.GetFormattedBookUseCase
import com.example.androidreader.domain.usecase.api.GetWordInfoUseCase
import com.example.androidreader.domain.usecase.api.GetWordWithDefinitionsUseCase
import com.example.androidreader.domain.usecase.impl.GetBookTitlesUseCaseImpl
import com.example.androidreader.domain.usecase.impl.GetFormattedBookUseCaseImpl
import com.example.androidreader.domain.usecase.impl.GetWordInfoUseCaseImpl
import com.example.androidreader.domain.usecase.impl.GetWordWithDefinitionsUseCaseImpl
import dagger.Binds
import dagger.Module

@Module
interface UsecaseModule {

    @Binds
    fun provideGetFormattedBookUseCase(bookUseCaseImpl: GetFormattedBookUseCaseImpl): GetFormattedBookUseCase

    @Binds
    fun provideGetWordWithDefinitionsUseCase(getWordWithDefinitionsUseCaseImpl: GetWordWithDefinitionsUseCaseImpl): GetWordWithDefinitionsUseCase

    @Binds
    fun provideGetWordInfoUseCase(getWordInfoUseCase: GetWordInfoUseCaseImpl): GetWordInfoUseCase

    @Binds
    fun provideGetBookTitlesUseCase(getBookTitlesUseCase: GetBookTitlesUseCaseImpl): GetBookTitlesUseCase
}