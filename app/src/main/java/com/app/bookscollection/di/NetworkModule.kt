package com.app.bookscollection.di

import android.content.Context
import com.app.bookscollection.data.api.BookApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    @RxNetwork
    fun provideRetrofitRx(@ApplicationContext context: Context): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://gutendex.com/")
            .client(OkHttpClient
                .Builder()
                .readTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS)
                .build())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    @CoroutinesNetwork
    fun provideRetrofit(@ApplicationContext context: Context): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://gutendex.com/")
            .client(OkHttpClient
                .Builder()
                .readTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS)
                .build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    @CoroutinesNetwork
    fun provideBookApiService(@CoroutinesNetwork retrofit: Retrofit): BookApiService {
        return retrofit.create(BookApiService::class.java)
    }

    @Provides
    @Singleton
    @RxNetwork
    fun provideBookApiServiceRx(@RxNetwork retrofit: Retrofit): BookApiService {
        return retrofit.create(BookApiService::class.java)
    }
}