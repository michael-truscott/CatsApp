package com.truscorp.catsapp.data.repositories

import com.truscorp.catsapp.data.api.CatsApi
import com.truscorp.catsapp.data.db.CatsAppDatabase
import com.truscorp.catsapp.data.db.models.FavouriteCat
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CatRepositoryImpl @Inject constructor(
    private val catsApi: CatsApi,
    private val catsAppDatabase: CatsAppDatabase
) : CatRepository {
    override suspend fun getAll(): Flow<List<Cat>> {
        val apiGetCatsFlow = flow {
            emit(catsApi.getAllCats())
        }
        return combine(apiGetCatsFlow, catsAppDatabase.favouriteCatDao().getAllFlow()) { apiResponse, favourites ->
            val body = apiResponse.body()
            if (!apiResponse.isSuccessful || body == null) {
                return@combine listOf()
            }
            return@combine body.map {
                Cat(
                    id = it.id,
                    imageUrl = "https://cataas.com/cat/${it.id}",
                    tags = it.tags,
                    isFavourite = favourites.contains(FavouriteCat(it.id))
                )
            }
        }
    }

    override suspend fun getById(id: String): Flow<Cat?> {
        val apiGetCatByIdFlow = flow {
            emit(catsApi.getCatById(id))
        }
        return combine(apiGetCatByIdFlow, catsAppDatabase.favouriteCatDao().getAllFlow()) { apiResponse, favourites ->
            val body = apiResponse.body()
            if (!apiResponse.isSuccessful || body == null) {
                return@combine null
            }
            return@combine Cat(
                id = id,
                imageUrl = "https://cataas.com/cat/${id}",
                tags = body.tags,
                isFavourite = favourites.contains(FavouriteCat(id))
            )
        }
    }

    override suspend fun setFavourite(id: String, favourite: Boolean) {
        if (favourite) {
            catsAppDatabase.favouriteCatDao().add(FavouriteCat(id))
        } else {
            catsAppDatabase.favouriteCatDao().delete(FavouriteCat(id))
        }
    }
}