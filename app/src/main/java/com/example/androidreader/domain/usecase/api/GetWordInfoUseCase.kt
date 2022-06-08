package com.example.androidreader.domain.usecase.api

import com.example.androidreader.domain.model.WordInfoDomainModel
import kotlinx.coroutines.flow.Flow

interface GetWordInfoUseCase {
   suspend fun getWordInfo(word: String)
   val wordInfoFlow: Flow<WordInfoDomainModel>
}