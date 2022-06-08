package com.example.androidreader.domain.usecase.impl

import com.example.androidreader.dagger.module.DefaultDispatcher
import com.example.androidreader.dagger.module.IoDispatcher
import com.example.androidreader.data.repository.api.BookRepository
import com.example.androidreader.data.repository.api.DictionaryRepository
import com.example.androidreader.domain.model.StrangeWordListDomainModel
import com.example.androidreader.domain.model.WordDefinitionDomainModel
import com.example.androidreader.domain.usecase.api.GetWordWithDefinitionsUseCase
import com.example.androidreader.utils.Stemmer
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetWordWithDefinitionsUseCaseImpl @Inject constructor(
    val bookRepository: BookRepository,
    val dictionaryRepository: DictionaryRepository,
    @DefaultDispatcher val dispatcher: CoroutineDispatcher,
) :
    GetWordWithDefinitionsUseCase {
    override val strangeWordListDomainModelFlow =
        MutableStateFlow(StrangeWordListDomainModel.empty())

    override suspend fun getStrangeWords(title: String) {
        bookRepository.getStrangeWords(title)
        withContext(dispatcher) {
            bookRepository.wordStatisticFlow.onEach { wordStatistic ->
                val strangeWordList =
                    StrangeWordListDomainModel(
                        title = title,
                        wordStatistic.words.associateBy { Stemmer.instance().getStemmingWord(it) }
                            .toMutableMap()
                    )

                val mentionsOfBook = bookRepository.getBookMentions(title)
                mentionsOfBook.mentions.forEach {
                    strangeWordList.wordWithDefinitionMap[Stemmer.instance().getStemmingWord(it).replaceFirstChar { it.uppercase() }] =
                        it
                }
                strangeWordListDomainModelFlow.emit(
                    strangeWordList
                )
            }.launchIn(this)
        }
    }
}