package com.truscorp.catsapp.data.db.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.truscorp.catsapp.data.db.models.FavouriteCat
import kotlinx.coroutines.flow.Flow

@Dao
interface FavouriteCatDao {

    @Query("SELECT * FROM FavouriteCat")
    fun getAllFlow(): Flow<List<FavouriteCat>>

    @Query("SELECT * FROM FavouriteCat")
    suspend fun getAll(): List<FavouriteCat>

    @Insert
    fun add(vararg favouriteCats: FavouriteCat)

    @Delete
    fun delete(vararg favouriteCats: FavouriteCat)
}