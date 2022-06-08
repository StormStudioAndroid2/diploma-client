package com.example.androidreader.data.model

data class WordStatisticDataModel(
    val title: String,
    val words: List<String>
) {

    companion object {
        fun empty() = WordStatisticDataModel("", mutableListOf())
    }
}