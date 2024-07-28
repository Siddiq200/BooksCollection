package com.app.bookscollection.data.repository

import com.app.bookscollection.data.db.BookDao
import com.app.bookscollection.data.model.BookResponse
import com.app.bookscollection.data.model.Resource
import com.app.bookscollection.domain.model.Book
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class FakeBookRepository(private val bookDao: BookDao) : BookRepository {

    private val fakeBooks: MutableList<Book> = mutableListOf()

    private val favoriteBooksFlow = MutableStateFlow<List<Book>>(emptyList())

    override fun searchBooksRx(page: Int, query: String): Single<Resource<BookResponse>> {
        throw NotImplementedError("Not implemented")
    }

    override suspend fun searchBooks(page: Int, query: String): Resource<BookResponse> {
        // Fake implementation for searchBooks
        val fakeResponse = generateFakeBookResponse()
        insertBooks(fakeResponse.results)
        return Resource.Success(fakeResponse)
    }

    override suspend fun getFavoriteBooks(): Flow<List<Book>> {
        return favoriteBooksFlow
    }

    override suspend fun updateFavoriteStatus(book: Book, isFavorite: Boolean) {
        val updatedBook = book.copy(isFavorite = isFavorite)
        fakeBooks.removeAll { it.id == book.id }
        fakeBooks.add(updatedBook)
        favoriteBooksFlow.value = fakeBooks.filter { it.isFavorite }
    }

    private fun generateFakeBookResponse(): BookResponse {
        val books = listOf(
            Book(
                id = 1,
                authors = listOf(Book.Author(name = "Author 1")),
                bookshelves = listOf("Shelf 1", "Shelf 2"),
                copyright = true,
                downloadCount = 100,
                formats = Book.Formats(
                    applicationepubzip = "epubUrl",
                    applicationoctetStream = "octetUrl",
                    applicationrdfxml = "rdfUrl",
                    applicationxMobipocketEbook = "mobiUrl",
                    imagejpeg = "jpegUrl",
                    texthtml = "htmlUrl",
                    textplainCharsetusAscii = "plainUrl"
                ),
                mediaType = "text",
                subjects = listOf("Subject 1", "Subject 2"),
                title = "Book Title 1",
                isFavorite = false
            ),
            Book(
                id = 2,
                authors = listOf(Book.Author(name = "Author 2")),
                bookshelves = listOf("Shelf 3", "Shelf 4"),
                copyright = false,
                downloadCount = 50,
                formats = Book.Formats(),
                mediaType = "image",
                subjects = listOf("Subject 3"),
                title = "Book Title 2",
                isFavorite = true
            )
        )
        return BookResponse(
            books.count(),
            null,
            null,
            books
        )
    }

    private suspend fun insertBooks(books: List<Book>) {
        fakeBooks.clear()
        fakeBooks.addAll(books)
        bookDao.insertBooks(books.map { it.toBookEntity() })
        favoriteBooksFlow.value = fakeBooks.filter { it.isFavorite }
    }
}