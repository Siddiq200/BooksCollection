package com.app.bookscollection.data.repository

import com.app.bookscollection.data.model.BookResponse
import com.app.bookscollection.data.model.Resource
import com.app.bookscollection.domain.model.Book
import kotlinx.coroutines.flow.Flow


interface BookRepository {
    suspend fun searchBooks(page: Int, query: String = ""): Resource<BookResponse>
    suspend fun getFavoriteBooks(): Flow<List<Book>>
    suspend fun updateFavoriteStatus(book: Book, isFavorite: Boolean)
}