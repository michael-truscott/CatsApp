package com.truscorp.catsapp.data.repositories.favourite

import com.truscorp.catsapp.data.db.CatsAppDatabase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FavouriteRepositoryImpl @Inject constructor(
    private val catsAppDatabase: CatsAppDatabase
) : FavouriteRepository {
    override fun getAll(): Flow<List<Favourite>> {
        return catsAppDatabase.favouriteCatDao().getAllFlow().map { result ->
            result.map { Favourite(id = it.id, imageUrl = "https://cataas.com/cat/${it.id}") }
        }
    }
}