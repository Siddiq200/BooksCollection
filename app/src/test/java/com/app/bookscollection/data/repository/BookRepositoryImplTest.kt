package com.app.bookscollection.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.app.bookscollection.MainCoroutineRule
import com.app.bookscollection.data.api.BookApiService
import com.app.bookscollection.data.db.BookDao
import com.app.bookscollection.data.model.BookEntity
import com.app.bookscollection.data.model.BookResponse
import com.app.bookscollection.data.model.Resource
import com.app.bookscollection.domain.model.Book
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations


class BookRepositoryImplTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    val instantExecutor = InstantTaskExecutorRule()

    @Mock
    private lateinit var mockBookDao: BookDao

    @Mock
    private lateinit var mockBookApiService: BookApiService

    private lateinit var repository: BookRepositoryImpl

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        repository = BookRepositoryImpl(mockBookDao, mockBookApiService, mockBookApiService)
    }
    @Test
    fun searchBooks() {
        val mockPage = 1
        val mockQuery = "search query"
        val mockResponse = Resource.Success(BookResponse(0,null,null,
            listOf(
                Book(0),
                Book(1)
            )
        ))

        runBlocking {
            `when`(mockBookApiService.getBooks(mockPage, mockQuery)).thenReturn(mockResponse.value)

            //Mock getAllBooks()
            val mockBooksFromDb = listOf<BookEntity>(BookEntity(0))
            `when`(mockBookDao.getAllBooks()).thenReturn(mockBooksFromDb)

            // Call the function to be tested
            val result = repository.searchBooks(mockPage, mockQuery)

            // Verify result
            MatcherAssert.assertThat(
                result,
                CoreMatchers.`is`(mockResponse)
            )
            assert(result is Resource.Success)
            //Books inserted in DB
            verify(mockBookDao).insertBooks(mockResponse.value.results.map { it.toBookEntity() })
        }
    }

    @Test
    fun getFavoriteBooks() {
        // Mock data
        val mockBookEntities = listOf(
            BookEntity(1, listOf(Book.Author(0,0,"Author A")),
                title = "Book 1"),
            BookEntity(2, listOf(Book.Author(0,0,"Author B")),
                title = "Book 2")
        )
        val expectedBooks = mockBookEntities.map { it.toBook() }

        // Mock behavior of DAO
        `when`(mockBookDao.getFavoriteBooksFlow()).thenReturn(flowOf(mockBookEntities))

        runBlocking {
            // Call the function to be tested
            val flow = repository.getFavoriteBooks()
            val collectedBooks = flow.toList()

            // Assert
            assert(collectedBooks.size == 1)
            assert(collectedBooks[0] == expectedBooks)
        }
    }

    @Test
    fun testUpdateFavoriteStatus() = runTest {
        // Mock data
        val mockBook = Book(id = 1, title = "Mock Book", isFavorite = false)
        val isFavorite = true

        // Call the function to be tested
        repository.updateFavoriteStatus(mockBook, isFavorite)
        advanceUntilIdle()
        verify(mockBookDao, times(1))
            .updateFavoriteStatus(mockBook.id, isFavorite)
    }
}