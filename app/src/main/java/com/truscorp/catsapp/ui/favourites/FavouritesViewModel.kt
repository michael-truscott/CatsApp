package com.truscorp.catsapp.ui.favourites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.truscorp.catsapp.data.repositories.favourite.FavouriteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouritesViewModel @Inject constructor(
    private val favouriteRepository: FavouriteRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<FavouritesUiState>(FavouritesUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            favouriteRepository.getAll()
                .catch {ex ->
                    _uiState.value = FavouritesUiState.Error("Error: ${ex.message}")
                }
                .collectLatest { list ->
                    _uiState.value = FavouritesUiState.Success(list)
                }
        }
    }
}