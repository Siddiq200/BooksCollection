package com.app.bookscollection.domain.usecases

import com.app.bookscollection.data.model.BookResponse
import com.app.bookscollection.data.model.Resource
import com.app.bookscollection.data.repository.BookRepository
import com.app.bookscollection.domain.model.Book
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

class GetLocalBooksUseCaseTest {
    private val mockRepository: BookRepository = mock(BookRepository::class.java)

    // Instance of GetLocalBooksUseCase with the mocked repository
    private val getLocalBooksUseCase = GetLocalBooksUseCase(mockRepository)

    @Test
    fun invokeGetLocalBooksUseCaseTest() = runTest {
        // Mock data
        val mockQuery = "Harry Potter"
        val mockBooks = listOf(
            Book(id = 1, title = "Harry Potter", isFavorite = false),
            Book(id = 1, title = "Harry Potter 2", isFavorite = false)
        )
        val mockResponse = Resource.Success(BookResponse(0,
            null,
            null,
            mockBooks))

        // Mock behavior: when repository.searchBooks(0, mockQuery) is called, return mockResponse
        `when`(mockRepository.searchBooks(0, mockQuery)).thenReturn(mockResponse)

        // Call the use case
        val result = getLocalBooksUseCase(mockQuery)

        // Assert that the result matches the mock response
        assertEquals(mockResponse, result)
    }
}