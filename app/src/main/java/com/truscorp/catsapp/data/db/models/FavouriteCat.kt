package com.truscorp.catsapp.data.db.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavouriteCat(
    @PrimaryKey val id: String
)