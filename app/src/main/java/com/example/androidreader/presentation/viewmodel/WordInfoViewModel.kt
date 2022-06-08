package com.example.androidreader.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidreader.domain.usecase.api.GetWordInfoUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class WordInfoViewModel @Inject constructor(
    private val getWordInfoUseCase: GetWordInfoUseCase
): ViewModel() {
    val wordInfoFlow = getWordInfoUseCase.wordInfoFlow

    init {

    }

    fun getWordByArgument(word: String) {
        viewModelScope.launch {
            getWordInfoUseCase.getWordInfo(word)
        }
    }
}