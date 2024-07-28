package com.app.bookscollection.data.api

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.app.bookscollection.data.model.Resource
import com.app.bookscollection.data.repository.BookRepository
import com.app.bookscollection.domain.model.Book
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.isActive
import java.util.concurrent.CancellationException

class BookPagingSource(
    private val repository: BookRepository,
    private val query: String
) : PagingSource<Int, Book>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Book> {
        return try {
            val nextPageNumber = params.key ?: 1

            if (currentCoroutineContext().isActive.not()) {
                return LoadResult.Error(CancellationException("Coroutine is cancelled"))
            }

            val response = repository.searchBooks(nextPageNumber, query)

            val books = (response as? Resource.Success)?.value?.results ?: arrayListOf()
            if (currentCoroutineContext().isActive.not()) {
                return LoadResult.Error(CancellationException("Coroutine is cancelled"))
            }

            val next = extractPageNumber((response as? Resource.Success)?.value?.next)

            LoadResult.Page(
                data = books,
                prevKey = null,
                nextKey = next
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    private fun extractPageNumber(nextUrl: String?): Int? {
        if (nextUrl.isNullOrEmpty()) {
            return null // Handle case where nextUrl is null or empty
        }
        val regex = Regex("page=(\\d+)")
        val matchResult = regex.find(nextUrl)

        return matchResult?.groupValues?.get(1)?.toIntOrNull()
    }
    override fun getRefreshKey(state: PagingState<Int, Book>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}