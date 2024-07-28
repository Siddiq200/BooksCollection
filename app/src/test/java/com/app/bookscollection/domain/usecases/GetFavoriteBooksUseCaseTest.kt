package com.app.bookscollection.domain.usecases

import com.app.bookscollection.data.repository.BookRepository
import com.app.bookscollection.domain.model.Book
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

class GetFavoriteBooksUseCaseTest {

    // Mocked repository
    private val mockRepository: BookRepository = mock(BookRepository::class.java)

    // Instance of GetFavoriteBooksUseCase with the mocked repository
    private val getFavoriteBooksUseCase = GetFavoriteBooksUseCase(mockRepository)

    @Test
    fun invokeGetFavoriteBooksUseCase() = runTest {
        // Mock data
        val mockFavoriteBooks = listOf(
            Book(id = 1, title = "Book 1", isFavorite = false),
            Book(id = 1, title = "Book 2", isFavorite = false)
        )
        val mockFlow: Flow<List<Book>> = flowOf(mockFavoriteBooks)

        `when`(mockRepository.getFavoriteBooks()).thenReturn(mockFlow)

        // Call the use case
        val resultFlow = getFavoriteBooksUseCase()

        //Verify
        val result = resultFlow.toList()
        assertEquals(mockFavoriteBooks, result[0])
    }
}