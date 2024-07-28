package com.app.bookscollection.presentation.offline

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.bookscollection.data.model.BookResponse
import com.app.bookscollection.data.model.Resource
import com.app.bookscollection.domain.model.Book
import com.app.bookscollection.domain.usecases.GetLocalBooksUseCase
import com.app.bookscollection.domain.usecases.UpdateFavoriteStatusUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OfflineViewModel @Inject constructor(
    private val updateFavoriteStatusUseCase: UpdateFavoriteStatusUseCase,
    private val getLocalBooksUseCase: GetLocalBooksUseCase
) : ViewModel() {

    private val _books: MutableLiveData<Resource<BookResponse>> = MutableLiveData()
    val books: LiveData<Resource<BookResponse>> = _books

    var lastQuery: String = ""

    fun updateFavoriteStatus(book: Book, isFavorite: Boolean) {
        viewModelScope.launch {
            updateFavoriteStatusUseCase(book, isFavorite)
        }
    }

    fun searchLocalBooks(query: String) {
        lastQuery = query
        viewModelScope.launch {
            val response = getLocalBooksUseCase(query)
            _books.postValue(response)

        }
    }

}