package com.example.androidreader.domain.usecase.impl

import com.example.androidreader.dagger.module.IoDispatcher
import com.example.androidreader.data.repository.api.BookRepository
import com.example.androidreader.domain.model.BookFormatDomainModel
import com.example.androidreader.domain.usecase.api.GetFormattedBookUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetFormattedBookUseCaseImpl @Inject constructor(
    @IoDispatcher val dispatcher: CoroutineDispatcher,
    private val bookRepository: BookRepository
) :
    GetFormattedBookUseCase {
    override val bookModelFlow =
        MutableStateFlow(BookFormatDomainModel.empty())

    override suspend fun getBook(title: String, wordMap: MutableMap<String, String>) {
        withContext(dispatcher) {
            bookRepository.getFormattedBook(title)
            bookRepository.bookFlow.onEach {
                bookModelFlow.emit(BookFormatDomainModel.convertBookDataModel(it, wordMap))
            }.launchIn(this)
        }
    }
}