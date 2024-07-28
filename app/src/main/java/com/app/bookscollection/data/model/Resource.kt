package com.app.bookscollection.data.model

import okhttp3.ResponseBody

sealed class Resource<out T> {
    data class Success<out T>(val value: T) : Resource<T>()

    data class Failure(
        val isNetworkError: Boolean,
        val errorCode: Int?,
        val errorBody: ResponseBody?,
        val errorString: String?,

        ) : Resource<Nothing>()

    data object Loading : Resource<Nothing>()

}