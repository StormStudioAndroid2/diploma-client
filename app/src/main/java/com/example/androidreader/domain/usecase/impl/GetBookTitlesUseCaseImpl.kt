package com.example.androidreader.domain.usecase.impl

import com.example.androidreader.data.repository.api.BookRepository
import com.example.androidreader.domain.model.BookTitleListDomainModel
import com.example.androidreader.domain.usecase.api.GetBookTitlesUseCase
import javax.inject.Inject

class GetBookTitlesUseCaseImpl @Inject constructor(
    private val bookRepository: BookRepository
) : GetBookTitlesUseCase {
    override suspend fun getTitles(): BookTitleListDomainModel {
        return BookTitleListDomainModel(bookRepository.getBookTitles().titles)
    }
}