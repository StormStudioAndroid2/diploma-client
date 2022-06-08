package com.example.androidreader.domain.usecase.api

import com.example.androidreader.domain.model.BookTitleListDomainModel

interface GetBookTitlesUseCase {

    suspend fun getTitles(): BookTitleListDomainModel
}