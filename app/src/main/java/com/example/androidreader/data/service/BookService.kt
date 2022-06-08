package com.example.androidreader.data.service

import com.example.androidreader.data.model.BookDataModel
import com.example.androidreader.data.model.MentionDataModel
import com.example.androidreader.data.model.WordStatisticDataModel
import retrofit2.http.GET
import retrofit2.http.Path

interface BookService {
    @GET("{title}/format_text")
    suspend fun getFormattedBooks(@Path("title") title: String): BookDataModel

    @GET("{title}/important_strange_words")
    suspend fun getWordStatistic(@Path("title") title: String): WordStatisticDataModel

    @GET("{title}/mentions")
    suspend fun getBookMentions(@Path(value = "title") title: String): List<String>

    @GET("mentions/{word}")
    suspend fun getTitleWithMention(@Path("word") word: String): MentionDataModel

    @GET("list")
    suspend fun getBookTitles(): List<String>
}