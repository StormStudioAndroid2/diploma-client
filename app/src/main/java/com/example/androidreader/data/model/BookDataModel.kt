package com.example.androidreader.data.model

class BookDataModel(val title: String, val formattedText: String) {

    companion object {
        fun empty(): BookDataModel = BookDataModel("", "")
    }
}