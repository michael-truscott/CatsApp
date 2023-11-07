package com.truscorp.catsapp.ui.favourites

import com.truscorp.catsapp.data.repositories.favourite.Favourite

sealed interface FavouritesUiState {
    object Loading : FavouritesUiState
    data class Error(val message: String) : FavouritesUiState
    data class Success(val favourites: List<Favourite>) : FavouritesUiState
}