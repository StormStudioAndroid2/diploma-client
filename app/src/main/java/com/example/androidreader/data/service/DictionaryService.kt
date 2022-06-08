package com.example.androidreader.data.service

import com.example.androidreader.data.model.WordDataModel
import retrofit2.http.GET
import retrofit2.http.Path

interface DictionaryService {
    @GET("en/{word}")
    suspend fun getWordDefinition(@Path("word") word: String): List<WordDataModel>
}