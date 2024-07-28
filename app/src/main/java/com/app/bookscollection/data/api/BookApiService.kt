package com.app.bookscollection.data.api

import com.app.bookscollection.data.model.BookResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface BookApiService {
    @GET("books/")
    suspend fun getBooks(@Query("page") page: Int = 1,
                         @Query("search") search: String = "",
                         @Query("languages") languages: String = "en"): BookResponse

    @GET("books/")
    fun getBooksRx(
        @Query("page") page: Int = 1,
        @Query("search") search: String = "",
        @Query("languages") languages: String = "en"
    ): Single<BookResponse>
}