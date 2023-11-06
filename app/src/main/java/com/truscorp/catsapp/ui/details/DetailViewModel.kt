package com.truscorp.catsapp.ui.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
    private val catRepository: CatRepository
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
}