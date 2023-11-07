package com.truscorp.catsapp.data.repositories

import kotlinx.coroutines.flow.Flow

interface CatRepository {

    suspend fun getAll(tags: List<String> = listOf()): Flow<List<Cat>>
    suspend fun getById(id: String): Flow<Cat?>
    suspend fun setFavourite(id: String, favourite: Boolean)
}