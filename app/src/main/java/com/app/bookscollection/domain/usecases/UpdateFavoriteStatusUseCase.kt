package com.app.bookscollection.domain.usecases

import com.app.bookscollection.data.repository.BookRepository
import com.app.bookscollection.di.RemoteRepository
import com.app.bookscollection.domain.model.Book
import javax.inject.Inject

class UpdateFavoriteStatusUseCase @Inject constructor(@RemoteRepository private val repository: BookRepository) {

    suspend operator fun invoke(book: Book, isFavorite: Boolean) {
        repository.updateFavoriteStatus(book, isFavorite)
    }
}