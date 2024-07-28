package com.app.bookscollection.data.repository

import com.app.bookscollection.data.api.BookApiService
import com.app.bookscollection.data.db.BookDao
import com.app.bookscollection.data.model.BookResponse
import com.app.bookscollection.data.model.Resource
import com.app.bookscollection.di.CoroutinesNetwork
import com.app.bookscollection.di.RxNetwork
import com.app.bookscollection.domain.model.Book
import com.app.bookscollection.utils.wrapEspressoIdlingResource
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.core.SingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import javax.inject.Inject

open class BookRepositoryImpl @Inject constructor(
    protected open val bookDao: BookDao,
    @RxNetwork private val bookApiServiceRx: BookApiService,
    @CoroutinesNetwork private val bookApiService: BookApiService
) : BaseRepository(), BookRepository {

    private suspend fun getBooks(page: Int, query: String) = safeApiCall {
        bookApiService.getBooks(page, query)
    }

    override fun searchBooksRx(page: Int, query: String): Single<Resource<BookResponse>> {
        return Single.create{ emitter ->
            val disposable = bookApiServiceRx.getBooksRx(page, query)
                .subscribeOn(Schedulers.io())
                .subscribe({ data ->
                    emitter.onSuccess(Resource.Success(data))
                }, { throwable ->
                    emitter.onError(throwable)
                })
            emitter.setDisposable(disposable)
        }
    }

    override suspend fun searchBooks(page: Int, query: String): Resource<BookResponse> {
        wrapEspressoIdlingResource {
            val response = getBooks(page, query)
            return if (response is Resource.Success && response.value.results.isNotEmpty()) {
                val booksFromDb = bookDao.getAllBooks()
                val favoriteBooks = booksFromDb.filter { it.isFavorite }
                response.value.results = response.value.results.map { book ->
                    book.isFavorite = favoriteBooks.any { it.id == book.id }
                    book
                }
                insertBooks(response.value.results)
                response
            }else{
                response
            }
        }
    }

    private suspend fun insertBooks(books: List<Book>){
        bookDao.insertBooks(books.map { it.toBookEntity() })
    }

    override suspend fun getFavoriteBooks(): Flow<List<Book>> {
        return bookDao.getFavoriteBooksFlow().map { bookEntities ->
            bookEntities.map { it.toBook() }
        }
    }

    override suspend fun updateFavoriteStatus(book: Book, isFavorite: Boolean) {
        withContext(Dispatchers.IO) {
            bookDao.updateFavoriteStatus(book.id, isFavorite)
        }
    }
}
