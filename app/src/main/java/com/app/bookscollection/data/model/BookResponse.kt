package com.app.bookscollection.data.model

import com.app.bookscollection.domain.model.Book

data class BookResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    var results: List<Book>
)