package com.example.androidreader.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidreader.domain.model.BookTitleListDomainModel
import com.example.androidreader.domain.usecase.api.GetBookTitlesUseCase
import com.example.androidreader.domain.usecase.api.GetWordInfoUseCase
import com.example.androidreader.presentation.model.PageViewStatus
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class BookListViewModel @Inject constructor(
    private val getBookTitlesUseCase: GetBookTitlesUseCase
) : ViewModel() {
    private val _bookTitleListDomainModelFlow = MutableSharedFlow<BookTitleListDomainModel>()
    val bookTitleListDomainModelFlow: SharedFlow<BookTitleListDomainModel>
        get() = _bookTitleListDomainModelFlow

    fun initViewModel() {
        viewModelScope.launch {
            _bookTitleListDomainModelFlow.emit(getBookTitlesUseCase.getTitles())
        }
    }
}