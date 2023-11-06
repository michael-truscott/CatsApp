package com.truscorp.catsapp.ui.details

import com.truscorp.catsapp.ui.common.CatUi

sealed interface DetailUiState {
    object Loading : DetailUiState
    data class Error(val message: String) : DetailUiState
    data class Success(val cat: CatUi) : DetailUiState
}