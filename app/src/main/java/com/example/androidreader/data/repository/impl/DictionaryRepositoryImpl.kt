package com.example.androidreader.data.repository.impl

import com.example.androidreader.data.model.WordDataModel
import com.example.androidreader.data.repository.api.DictionaryRepository
import com.example.androidreader.data.service.DictionaryService
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class DictionaryRepositoryImpl @Inject constructor(
    private val dictionaryService: DictionaryService,
) : DictionaryRepository {
    override val definitionFlow = MutableStateFlow(WordDataModel("", listOf()))
    override suspend fun getWordDefinition(word: String) {
        definitionFlow.emit(dictionaryService.getWordDefinition(word).first())
    }
}