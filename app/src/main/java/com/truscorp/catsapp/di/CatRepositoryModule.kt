package com.truscorp.catsapp.di

import com.truscorp.catsapp.data.repositories.CatRepository
import com.truscorp.catsapp.data.repositories.CatRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class CatRepositoryModule {

    @Binds
    abstract fun bindCatRepository(
        catRepositoryImpl: CatRepositoryImpl
    ): CatRepository
}