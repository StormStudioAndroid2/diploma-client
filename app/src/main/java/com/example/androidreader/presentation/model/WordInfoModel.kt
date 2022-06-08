package com.example.androidreader.presentation.model

data class WordInfoModel (
    val word: String,
    val definitions: List<String>,
    val booksWithMention: List<String>
)