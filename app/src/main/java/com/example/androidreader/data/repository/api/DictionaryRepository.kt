package com.example.androidreader.data.repository.api

import com.example.androidreader.data.model.WordDataModel
import com.example.androidreader.domain.model.WordDefinitionDomainModel
import kotlinx.coroutines.flow.Flow


interface DictionaryRepository {
   suspend fun getWordDefinition(word: String)
   val definitionFlow: Flow<WordDataModel>
}