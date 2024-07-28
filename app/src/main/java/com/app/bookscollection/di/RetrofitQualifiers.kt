package com.app.bookscollection.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class RxNetwork

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class CoroutinesNetwork