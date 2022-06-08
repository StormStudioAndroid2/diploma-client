package com.example.androidreader.domain.model

import com.example.androidreader.data.model.WordStatisticDataModel
import com.example.androidreader.utils.Stemmer

data class StrangeWordListDomainModel(
    val title: String,
    val wordWithDefinitionMap: MutableMap<String, String>
) {
    companion object {
        fun convertToStrangeWordListDomainModel(wordStatisticDataModel: WordStatisticDataModel) =
            StrangeWordListDomainModel(
                title = wordStatisticDataModel.title,
                wordStatisticDataModel.words.associateBy { Stemmer.instance().getStemmingWord(it) }
                    .toMutableMap()
            )

        fun empty() = StrangeWordListDomainModel("", mutableMapOf())
    }
}