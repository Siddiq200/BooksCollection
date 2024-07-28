package com.app.bookscollection.presentation.books

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.bookscollection.domain.model.Book
import com.app.bookscollection.domain.usecases.UpdateFavoriteStatusUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookViewModel @Inject constructor(
    private val updateFavoriteStatusUseCase: UpdateFavoriteStatusUseCase
) : ViewModel() {

    fun updateFavoriteStatus(book: Book, isFavorite: Boolean) {
        viewModelScope.launch {
            updateFavoriteStatusUseCase(book, isFavorite)
        }
    }

}