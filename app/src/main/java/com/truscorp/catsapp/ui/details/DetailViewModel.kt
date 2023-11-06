package com.truscorp.catsapp.ui.details

import androidx.lifecycle.SavedStateHandle
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
class DetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val catRepository: CatRepository,
    private val catsAppDatabase: CatsAppDatabase
) : ViewModel() {

    private val id = requireNotNull(savedStateHandle.get<String>("id"))

    private val _uiState = MutableStateFlow<DetailUiState>(DetailUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        _uiState.value = DetailUiState.Loading

        viewModelScope.launch {
            catRepository.getById(id).collect {
                if (it == null) {
                    _uiState.value = DetailUiState.Error("Error: Cat not found")
                } else {
                    _uiState.value = DetailUiState.Success(
                        cat = it.toCatUi()
                    )
                }
            }
        }
    }

    fun performAction(action: DetailUiAction) {
        when (action) {
            is DetailUiAction.SetFavourite -> setFavourite(action.favourite)
        }
    }

    fun setFavourite(favourite: Boolean) {
        viewModelScope.launch {
            if (favourite) {
                catsAppDatabase.favouriteCatDao().add(FavouriteCat(id))
            } else {
                catsAppDatabase.favouriteCatDao().delete(FavouriteCat(id))
            }
        }
    }
}

sealed interface DetailUiAction {
    data class SetFavourite(val favourite: Boolean) : DetailUiAction
}