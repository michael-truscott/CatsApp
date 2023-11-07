package com.truscorp.catsapp.ui.tag_results

import com.truscorp.catsapp.ui.common.CatUi

sealed interface TagResultsUiState {
    object Loading : TagResultsUiState
    data class Error(val message: String) : TagResultsUiState
    data class Success(val catList: List<CatUi>) : TagResultsUiState
}