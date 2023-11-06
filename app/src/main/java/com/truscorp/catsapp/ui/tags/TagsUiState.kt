package com.truscorp.catsapp.ui.tags

sealed interface TagsUiState {
    object Loading : TagsUiState
    data class Error(val message: String) : TagsUiState
    data class Success(val tags: List<String>) : TagsUiState
}
