package com.example.androidreader.dagger.module

import com.example.androidreader.data.repository.api.BookRepository
import com.example.androidreader.data.repository.api.DictionaryRepository
import com.example.androidreader.data.repository.impl.BookRepositoryImpl
import com.example.androidreader.data.repository.impl.DictionaryRepositoryImpl
import dagger.Binds
import dagger.Module

@Module
interface RepositoryModule {

    @Binds
    fun provideBookRepository(bookRepositoryImpl: BookRepositoryImpl): BookRepository

    @Binds
    fun provideDictionaryRepository(dictionaryRepositoryImpl: DictionaryRepositoryImpl): DictionaryRepository
}