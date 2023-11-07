package com.truscorp.catsapp.di

import com.truscorp.catsapp.data.repositories.cat.CatRepository
import com.truscorp.catsapp.data.repositories.cat.CatRepositoryImpl
import com.truscorp.catsapp.data.repositories.favourite.FavouriteRepository
import com.truscorp.catsapp.data.repositories.favourite.FavouriteRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoriesModule {

    @Binds
    abstract fun bindCatRepository(
        catRepositoryImpl: CatRepositoryImpl
    ): CatRepository

    @Binds
    abstract fun bindFavouriteRepository(
        favouriteRepositoryImpl: FavouriteRepositoryImpl
    ): FavouriteRepository
}