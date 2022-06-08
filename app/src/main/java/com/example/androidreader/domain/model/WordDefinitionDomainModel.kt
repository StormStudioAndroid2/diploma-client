package com.example.androidreader.domain.model

import com.example.androidreader.data.model.WordDataModel

data class WordDefinitionDomainModel(
    val word: String,
    val definitions: List<String>
) {
    companion object {
        fun notFoundObject(word: String) = WordDefinitionDomainModel(word, listOf("Not found"))
        fun empty() = WordDefinitionDomainModel("", listOf())
        fun convert(wordDataModel: WordDataModel) = WordDefinitionDomainModel(
            wordDataModel.word,
            wordDataModel.meanings.flatMap { meaning ->
                meaning.definitions.map { it.definition }
            }
        )
    }
}