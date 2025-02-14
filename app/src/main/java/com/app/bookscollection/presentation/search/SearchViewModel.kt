package com.app.bookscollection.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.app.bookscollection.data.api.BookPagingSource
import com.app.bookscollection.data.repository.BookRepository
import com.app.bookscollection.di.RemoteRepository
import com.app.bookscollection.domain.model.Book
import com.app.bookscollection.domain.usecases.UpdateFavoriteStatusUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    @RemoteRepository private val repository: BookRepository,
    private val updateFavoriteStatusUseCase: UpdateFavoriteStatusUseCase
) : ViewModel() {

    private var currentQuery: String = ""

    fun fetchBooks(query: String): Flow<PagingData<Book>> {
        currentQuery = query
        return Pager(
            config = PagingConfig(pageSize = PAGE_SIZE,
                initialLoadSize = PAGE_SIZE,
                enablePlaceholders = false),
            pagingSourceFactory = { BookPagingSource(repository, currentQuery) }
        ).flow.cachedIn(viewModelScope)
    }

    fun updateFavoriteStatus(book: Book, isFavorite: Boolean) {
        viewModelScope.launch {
            updateFavoriteStatusUseCase(book, isFavorite)
        }
    }

    companion object {
        private const val PAGE_SIZE = 32
    }
}