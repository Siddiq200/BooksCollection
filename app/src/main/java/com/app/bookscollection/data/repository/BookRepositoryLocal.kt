package com.app.bookscollection.data.repository

import com.app.bookscollection.data.api.BookApiService
import com.app.bookscollection.data.db.BookDao
import com.app.bookscollection.data.model.BookResponse
import com.app.bookscollection.data.model.Resource
import com.app.bookscollection.di.CoroutinesNetwork
import com.app.bookscollection.di.RxNetwork
import javax.inject.Inject

class BookRepositoryLocal @Inject constructor(
    override val bookDao: BookDao,
    @RxNetwork private val bookApiServiceRx: BookApiService,
    @CoroutinesNetwork private val bookApiService: BookApiService
) : BookRepositoryImpl(bookDao, bookApiServiceRx, bookApiService) {

    override suspend fun searchBooks(page: Int, query: String): Resource<BookResponse> {
        val books = bookDao.searchBooks("%$query%").map { it.toBook() }
        val response = BookResponse(
            books.count(),
            null,
            null,
            books
        )
        return Resource.Success(response)
    }
}
