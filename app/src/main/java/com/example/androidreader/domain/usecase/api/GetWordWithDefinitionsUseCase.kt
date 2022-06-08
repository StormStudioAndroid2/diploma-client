package com.example.androidreader.domain.usecase.api

import com.example.androidreader.domain.model.StrangeWordListDomainModel
import kotlinx.coroutines.flow.Flow

interface GetWordWithDefinitionsUseCase {
    val strangeWordListDomainModelFlow: Flow<StrangeWordListDomainModel>

    suspend fun getStrangeWords(title: String)
}