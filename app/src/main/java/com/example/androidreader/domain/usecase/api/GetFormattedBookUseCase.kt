package com.example.androidreader.domain.usecase.api

import com.example.androidreader.domain.model.BookFormatDomainModel
import kotlinx.coroutines.flow.Flow

interface GetFormattedBookUseCase {

    val bookModelFlow: Flow<BookFormatDomainModel>

    suspend fun getBook(title: String, wordMap: MutableMap<String, String>)
}