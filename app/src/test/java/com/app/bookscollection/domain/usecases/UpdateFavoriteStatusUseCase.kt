package com.app.bookscollection.domain.usecases

import com.app.bookscollection.data.repository.BookRepository
import com.app.bookscollection.domain.model.Book
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

class UpdateFavoriteStatusUseCaseTest {

    // Mocked repository
    private val mockRepository: BookRepository = mock(BookRepository::class.java)

    // Instance of UpdateFavoriteStatusUseCase with the mocked repository
    private val updateFavoriteStatusUseCase = UpdateFavoriteStatusUseCase(mockRepository)

    @Test
    fun invokeUpdateFavoriteStatusUseCaseTest() = runTest {
        // Mock data
        val mockBook = Book(id = 1, title = "Book 1", isFavorite = false)
        val mockIsFavorite = true

        // Call the use case
        updateFavoriteStatusUseCase(mockBook, mockIsFavorite)

        // Verify that repository.updateFavoriteStatus was called with the correct parameters
        verify(mockRepository).updateFavoriteStatus(mockBook, mockIsFavorite)
        Mockito.verifyNoMoreInteractions(mockRepository)
    }
}