package com.app.bookscollection.domain.usecases

import com.app.bookscollection.data.repository.BookRepository
import com.app.bookscollection.domain.model.Book
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoriteBooksUseCase @Inject constructor(private val repository: BookRepository) {
    suspend operator fun invoke(): Flow<List<Book>> {
        return repository.getFavoriteBooks()
    }
}