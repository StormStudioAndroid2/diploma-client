package com.example.androidreader.data.repository.impl

import com.example.androidreader.data.model.BookDataModel
import com.example.androidreader.data.model.BookTitleListModel
import com.example.androidreader.data.model.MentionDataModel
import com.example.androidreader.data.model.WordStatisticDataModel
import com.example.androidreader.data.repository.api.BookRepository
import com.example.androidreader.data.service.BookService
import kotlinx.coroutines.flow.MutableStateFlow
import java.lang.Exception
import javax.inject.Inject

class BookRepositoryImpl @Inject constructor(
    private val bookRetrofit: BookService,
) : BookRepository {
    override val bookFlow = MutableStateFlow(BookDataModel.empty())
    override val wordStatisticFlow = MutableStateFlow(WordStatisticDataModel.empty())

    override suspend fun getFormattedBook(title: String) {
        try {
            bookFlow.emit(bookRetrofit.getFormattedBooks(title))
        } catch (ex: Exception) {
            throw ex
        }
    }

    override suspend fun getStrangeWords(title: String) {
        try {
            wordStatisticFlow.emit(bookRetrofit.getWordStatistic(title.replace(" ", "_")))
        } catch (ex: Exception) {
            throw  ex
        }
    }

    override suspend fun getTitlesWithMention(word: String): MentionDataModel {
        return bookRetrofit.getTitleWithMention(word)
    }

    override suspend fun getBookMentions(title: String): MentionDataModel {
        return MentionDataModel(bookRetrofit.getBookMentions(title.replace(" ", "_")))
    }

    override suspend fun getBookTitles(): BookTitleListModel {
        return BookTitleListModel(bookRetrofit.getBookTitles())
    }
}