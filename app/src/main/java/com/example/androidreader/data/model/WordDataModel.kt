package com.example.androidreader.data.model

class WordDataModel(
    val word: String,
    val meanings: List<MeaningDataModel>
) {
    companion object {
        fun empty() = WordDataModel("", listOf())
    }
}