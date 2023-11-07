package com.truscorp.catsapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.truscorp.catsapp.data.db.CatsAppDatabase
import com.truscorp.catsapp.data.db.models.FavouriteCat
import com.truscorp.catsapp.data.repositories.CatRepository
import com.truscorp.catsapp.ui.common.toCatUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val catRepository: CatRepository,
    private val catsAppDatabase: CatsAppDatabase
) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        refresh()
    }

    fun performAction(action: HomeUiAction) {
        when (action) {
            is HomeUiAction.Refresh -> refresh()
            is HomeUiAction.SetFavourite -> setFavourite(action.id, action.favourite)
        }
    }

    private fun setFavourite(id: String, favourite: Boolean) {
        viewModelScope.launch {
            if (favourite) {
                catsAppDatabase.favouriteCatDao().add(FavouriteCat(id))
            } else {
                catsAppDatabase.favouriteCatDao().delete(FavouriteCat(id))
            }
        }
    }

    private fun refresh() {
        _uiState.value = HomeUiState.Loading

        viewModelScope.launch {
            catRepository.getAll().collect { list ->
                _uiState.value = HomeUiState.Success(list.map { it.toCatUi() })
            }
        }
    }
}