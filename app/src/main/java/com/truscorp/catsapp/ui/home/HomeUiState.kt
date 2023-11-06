package com.truscorp.catsapp.ui.home

import com.truscorp.catsapp.ui.common.CatUi

sealed interface HomeUiState {
    object Loading : HomeUiState
    data class Error(val message: String) : HomeUiState
    data class Success(val catList: List<CatUi>) : HomeUiState
}

sealed interface HomeUiAction {
    object Refresh : HomeUiAction
}
