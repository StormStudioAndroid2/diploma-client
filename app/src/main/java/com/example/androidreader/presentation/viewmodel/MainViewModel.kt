package com.example.androidreader.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidreader.domain.model.StrangeWordListDomainModel
import com.example.androidreader.domain.usecase.api.GetFormattedBookUseCase
import com.example.androidreader.domain.usecase.api.GetWordInfoUseCase
import com.example.androidreader.domain.usecase.api.GetWordWithDefinitionsUseCase
import com.example.androidreader.presentation.model.PageViewStatus
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val getFormattedBookUseCase: GetFormattedBookUseCase,
    private val getWordWithDefinitionsUseCase: GetWordWithDefinitionsUseCase,
    private val getWordInfoUseCase: GetWordInfoUseCase
) :
    ViewModel() {
    private var title = ""
    private var wordMap: Map<String, String> = mapOf()
    val bookFlow = getFormattedBookUseCase.bookModelFlow
    private val _pageViewStatusFlow = MutableSharedFlow<PageViewStatus>()
    val pageViewStatusFlow: SharedFlow<PageViewStatus>
        get() = _pageViewStatusFlow.asSharedFlow()
    val strangeWordListDomainModelFlow =
        getWordWithDefinitionsUseCase.strangeWordListDomainModelFlow

    fun initViewModel(title: String) {
        this.title = title
        subscribeToFlows()
        viewModelScope.launch {
            getWordWithDefinitionsUseCase.getStrangeWords(title)
        }
    }

    fun onPreviousButtonClick() {
        viewModelScope.launch {
            _pageViewStatusFlow.emit(PageViewStatus.PREVIOUS)
        }
    }

    fun onNextButtonClick() {
        viewModelScope.launch {
            _pageViewStatusFlow.emit(PageViewStatus.NEXT)
        }
    }

    fun onWordInPageViewClicked(word: String) {
        viewModelScope.launch {
            getWordInfoUseCase.getWordInfo(word)
        }
    }

    private fun subscribeToFlows() {
        viewModelScope.launch {
            strangeWordListDomainModelFlow.onEach {
                if (it != StrangeWordListDomainModel.empty()) {
                    getFormattedBookUseCase.getBook(title, it.wordWithDefinitionMap)
                    wordMap = it.wordWithDefinitionMap
                }
            }.launchIn(viewModelScope)
        }
        getWordInfoUseCase.wordInfoFlow.onEach {
            it
        }.launchIn(viewModelScope)
    }
}