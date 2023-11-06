package com.truscorp.catsapp.ui.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.truscorp.catsapp.data.api.CatsApi
import com.truscorp.catsapp.ui.common.CatUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val catsApi: CatsApi
) : ViewModel() {

    private val id = requireNotNull(savedStateHandle.get<String>("id"))

    private val _uiState = MutableStateFlow<DetailUiState>(DetailUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            _uiState.value = DetailUiState.Loading
            delay(2000)
            try {
                val result = catsApi.getCatById(id)
                if (!result.isSuccessful) {
                    _uiState.value = DetailUiState.Error("Request failed with status code ${result.code()}")
                    return@launch
                }
                val body = result.body()
                if (body == null) {
                    _uiState.value = DetailUiState.Error("A null response was received")
                    return@launch
                }

                _uiState.value = DetailUiState.Success(
                    cat = CatUi(id = body.id, imageUrl = "https://cataas.com/cat/${body.id}", tags = body.tags, isFavourite = false)
                )
            } catch (ex: Exception) {
                _uiState.value = DetailUiState.Error("Error: ${ex.message}")
            }
        }
    }
}