package com.app.bookscollection.presentation.favourite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.bookscollection.domain.model.Book
import com.app.bookscollection.domain.usecases.GetFavoriteBooksUseCase
import com.app.bookscollection.domain.usecases.UpdateFavoriteStatusUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouriteViewModel @Inject constructor(
    private val updateFavoriteStatusUseCase: UpdateFavoriteStatusUseCase,
    private val getFavoriteBooksUseCase: GetFavoriteBooksUseCase
) : ViewModel() {

    private val _favoriteBooks: MutableStateFlow<List<Book>> = MutableStateFlow(emptyList())
    val favoriteBooks: StateFlow<List<Book>> = _favoriteBooks

    init {
        fetchFavoriteBooks()
    }

    fun updateFavoriteStatus(book: Book, isFavorite: Boolean) {
        viewModelScope.launch {
            updateFavoriteStatusUseCase(book, isFavorite)
        }
    }

    fun fetchFavoriteBooks() {
        _favoriteBooks.value = emptyList()
        viewModelScope.launch {
            getFavoriteBooksUseCase().collect { result ->
                _favoriteBooks.value = result
            }
        }
    }

}