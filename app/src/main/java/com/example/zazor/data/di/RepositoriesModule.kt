package com.example.zazor.data.di

import com.example.zazor.data.repositories.PhotoRepository
import com.example.zazor.data.repositories.PhotoRepositoryImpl
import com.example.zazor.data.storage.AppDatabaseCreator
import org.koin.dsl.module

val repositoriesModule = module {
    single { AppDatabaseCreator.database.photosDao() }
    single<PhotoRepository> { PhotoRepositoryImpl(get()) }
}