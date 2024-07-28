package com.app.bookscollection.presentation.search

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.paging.PagingData
import com.app.bookscollection.data.api.BookApiService
import com.app.bookscollection.data.db.BookDao
import com.app.bookscollection.data.repository.BookRepository
import com.app.bookscollection.data.repository.BookRepositoryImpl
import com.app.bookscollection.domain.model.Book
import com.app.bookscollection.domain.usecases.UpdateFavoriteStatusUseCase
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class SearchViewModelTest {

    // Mock dependencies
    @Mock
    private lateinit var mockBookDao: BookDao
    @Mock
    private lateinit var mockBookApiService: BookApiService

    private lateinit var  repository: BookRepository

    private lateinit var  updateFavoriteStatusUseCase: UpdateFavoriteStatusUseCase

    // Class under test
    private lateinit var viewModel: SearchViewModel

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        repository = BookRepositoryImpl(mockBookDao, mockBookApiService, mockBookApiService)
        updateFavoriteStatusUseCase = UpdateFavoriteStatusUseCase(repository)
        viewModel = SearchViewModel(repository, updateFavoriteStatusUseCase)
    }

    @Test
    fun fetchBooks() {
        val query = "testQuery"

        // Mock response from repository
        val mockPagingData: PagingData<Book> = PagingData.from(listOf(
            Book(id = 1, title = "Book 1"),
            Book(id = 2, title = "Book 2")
        ))
    }

    @Test
    fun updateFavoriteStatus() = runTest {
        val mockBook = Book(id = 1, title = "Test Book")
        val isFavorite = true

        `when`(mockBookDao.updateFavoriteStatus(1,true)).thenReturn(Unit)

        // Call method under test
        viewModel.updateFavoriteStatus(mockBook, isFavorite)

        // Verify
        verify(updateFavoriteStatusUseCase).invoke(mockBook, isFavorite)
    }
}