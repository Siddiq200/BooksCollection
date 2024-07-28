package com.app.bookscollection.data.repository

import com.app.bookscollection.data.model.BookResponse
import com.app.bookscollection.data.model.Resource
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException


abstract class BaseRepository {
    suspend fun <T> safeApiCall(
        apiCall: suspend () -> T
    ): Resource<T> {
        return withContext(Dispatchers.IO) {
            try {
                Resource.Success(apiCall.invoke())
            } catch (throwable: Throwable) {
                when (throwable) {
                    is HttpException -> {
                        Resource.Failure(
                            false,
                            throwable.code(),
                            throwable.response()?.errorBody(),
                            throwable.message()
                        )
                    }
                    else -> {
                        Resource.Failure(true, null, null, throwable.message)
                    }
                }
            }
        }
    }

    fun <T : Any> safeApiCallRx(apiCall: () -> Single<T>): Single<T> {
        return Single.create { emitter ->
            val disposable = apiCall.invoke()
                .subscribeOn(Schedulers.io())
                .subscribe({ data ->
                    emitter.onSuccess(data)
                }, { throwable ->
                    emitter.onError(throwable)
                })
            emitter.setDisposable(disposable)
        }
    }

}