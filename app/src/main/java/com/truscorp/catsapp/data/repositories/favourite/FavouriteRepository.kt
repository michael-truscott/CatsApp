package com.truscorp.catsapp.data.repositories.favourite

import kotlinx.coroutines.flow.Flow

interface FavouriteRepository {

    fun getAll(): Flow<List<Favourite>>
}