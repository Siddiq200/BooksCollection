package com.app.bookscollection.domain.usecases

import com.app.bookscollection.data.model.BookResponse
import com.app.bookscollection.data.model.Resource
import com.app.bookscollection.data.repository.BookRepository
import com.app.bookscollection.di.LocalRepository
import com.app.bookscollection.domain.model.Book
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLocalBooksUseCase @Inject constructor(@LocalRepository private val localBookRepository: BookRepository) {
    suspend operator fun invoke(query: String): Resource<BookResponse> {
        return localBookRepository.searchBooks(0, query)
    }
}