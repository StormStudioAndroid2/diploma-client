package com.example.androidreader.domain.model

import com.example.androidreader.data.model.BookDataModel
import com.example.androidreader.pageView.model.PageText
import com.example.androidreader.pageView.converter.PageTextConverter

data class BookFormatDomainModel(
    val title: String,
    val textList: List<PageText>
) {

    companion object {
        fun convertBookDataModel(
            bookDataModel: BookDataModel,
            wordMap: MutableMap<String, String>
        ) = BookFormatDomainModel(
            title = bookDataModel.title,
            textList = PageTextConverter.convertStringToPageTextList(
                bookDataModel.formattedText,
                wordMap
            )
        )

        fun empty() = BookFormatDomainModel("", emptyList())
    }
}