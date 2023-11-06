package com.truscorp.catsapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.truscorp.catsapp.data.db.daos.FavouriteCatDao
import com.truscorp.catsapp.data.db.models.FavouriteCat

@Database(entities = [FavouriteCat::class], version = 1)
abstract class CatsAppDatabase : RoomDatabase() {

    abstract fun favouriteCatDao(): FavouriteCatDao
}