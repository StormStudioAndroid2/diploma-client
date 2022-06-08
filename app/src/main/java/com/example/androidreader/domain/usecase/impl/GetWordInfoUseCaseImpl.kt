package com.example.androidreader.domain.usecase.impl

import com.example.androidreader.dagger.module.IoDispatcher
import com.example.androidreader.data.repository.api.BookRepository
import com.example.androidreader.data.repository.api.DictionaryRepository
import com.example.androidreader.domain.model.WordInfoDomainModel
import com.example.androidreader.domain.usecase.api.GetWordInfoUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject

class GetWordInfoUseCaseImpl @Inject constructor(
    @IoDispatcher val dispatcher: CoroutineDispatcher,
    val dictionaryRepository: DictionaryRepository,
    val bookRepository: BookRepository
) : GetWordInfoUseCase {
    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        println("Handle $exception in CoroutineExceptionHandler")
    }

    override val wordInfoFlow = MutableStateFlow(WordInfoDomainModel.empty())

    override suspend fun getWordInfo(word: String) {
        try {
            withContext(dispatcher + coroutineExceptionHandler) {
                try {
                    dictionaryRepository.getWordDefinition(word)
                } catch (ex: Exception) {
                    val mentionDataModel = async { bookRepository.getTitlesWithMention(word) }.await()
                    wordInfoFlow.emit(WordInfoDomainModel(word.replaceFirstChar { it.uppercase() }, "not found word", mentionDataModel.mentions))
                }
                dictionaryRepository.definitionFlow.onEach {
                    val mentionDataModel = async { bookRepository.getTitlesWithMention(word) }.await()
                    wordInfoFlow.emit(
                        WordInfoDomainModel(
                            word,
                            it.meanings.first().definitions.first().definition,
                            mentionDataModel.mentions
                        )
                    )
                }.launchIn(this)
            }
        } catch (ex: Exception) {


        }
    }
}