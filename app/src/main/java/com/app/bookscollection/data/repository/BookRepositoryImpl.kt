package com.app.bookscollection.data.repository

import com.app.bookscollection.data.api.BookApiService
import com.app.bookscollection.data.db.BookDao
import com.app.bookscollection.data.model.BookResponse
import com.app.bookscollection.data.model.Resource
import com.app.bookscollection.domain.model.Book
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

open class BookRepositoryImpl @Inject constructor(
    protected open val bookDao: BookDao,
    private val bookApiService: BookApiService
) : BaseRepository(), BookRepository {

    private suspend fun getBooks(page: Int, query: String) = safeApiCall {
        bookApiService.getBooks(page, query)
    }

    override suspend fun searchBooks(page: Int, query: String): Resource<BookResponse> {
        val response = getBooks(page, query)
        return if (response is Resource.Success && response.value.results.isNotEmpty()) {
            val booksFromDb = bookDao.getAllBooks()
            val favoriteBooks = booksFromDb.filter { it.isFavorite }
            response.value.results = response.value.results.map { book ->
                book.isFavorite = favoriteBooks.any { it.id == book.id }
                book
            }
            bookDao.insertBooks(response.value.results.map { it.toBookEntity() })
            response
        }else{
            response
        }
    }

    override suspend fun getFavoriteBooks(): Flow<List<Book>> {
        return bookDao.getFavoriteBooksFlow().map { bookEntities ->
            bookEntities.map { it.toBook() }
        }
    }

    override suspend fun updateFavoriteStatus(book: Book, isFavorite: Boolean) {
        withContext(Dispatchers.IO) {
            bookDao.updateFavoriteStatus(book.id, isFavorite)
        }
    }
}
