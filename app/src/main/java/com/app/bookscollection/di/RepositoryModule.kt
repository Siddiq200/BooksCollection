package com.app.bookscollection.di

import com.app.bookscollection.data.api.BookApiService
import com.app.bookscollection.data.db.BookDao
import com.app.bookscollection.data.repository.BookRepository
import com.app.bookscollection.data.repository.BookRepositoryImpl
import com.app.bookscollection.data.repository.BookRepositoryLocal
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    @RemoteRepository
    fun provideBookRepository(apiService: BookApiService, bookDao: BookDao): BookRepository {
        return BookRepositoryImpl(bookDao, apiService)
    }

    @Provides
    @Singleton
    @LocalRepository
    fun provideLocalBookRepository(apiService: BookApiService, bookDao: BookDao): BookRepository {
        return BookRepositoryLocal(bookDao, apiService)
    }
}