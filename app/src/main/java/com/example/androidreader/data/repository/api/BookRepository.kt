package com.example.androidreader.data.repository.api

import com.example.androidreader.data.model.BookDataModel
import com.example.androidreader.data.model.BookTitleListModel
import com.example.androidreader.data.model.MentionDataModel
import com.example.androidreader.data.model.WordStatisticDataModel
import kotlinx.coroutines.flow.Flow

interface BookRepository {

    val bookFlow: Flow<BookDataModel>
    val wordStatisticFlow: Flow<WordStatisticDataModel>

    suspend fun getFormattedBook(title: String)

    suspend fun getStrangeWords(title: String)

    suspend fun getTitlesWithMention(word: String): MentionDataModel

    suspend fun getBookMentions(title: String): MentionDataModel

    suspend fun getBookTitles(): BookTitleListModel
}