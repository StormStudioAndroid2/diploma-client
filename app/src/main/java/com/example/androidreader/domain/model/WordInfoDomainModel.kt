package com.example.androidreader.domain.model

data class WordInfoDomainModel(
    val word: String,
    val definition: String,
    val mentions: List<String>
) {

    companion object {
        fun empty() = WordInfoDomainModel("", "Not found definition", emptyList())

        fun empty(word: String) = WordInfoDomainModel(word, "Not found definition", emptyList())

    }
}