package com.truscorp.catsapp.ui.tags

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.truscorp.catsapp.data.api.CatsApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TagsViewModel @Inject constructor(
    private val catsApi: CatsApi
) : ViewModel() {

    private val _uiState: MutableStateFlow<TagsUiState> = MutableStateFlow(TagsUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        refresh()
    }

    fun refresh() {
        _uiState.value = TagsUiState.Loading
        viewModelScope.launch {
            try {
                val result = catsApi.tags()
                if (!result.isSuccessful) {
                    _uiState.value = TagsUiState.Error("Request failed with status code ${result.code()}")
                    return@launch
                }
                val body = result.body()
                if (body == null) {
                    _uiState.value = TagsUiState.Error("A null response was received")
                    return@launch
                }

                _uiState.value = TagsUiState.Success(
                    tags = body.filter { it.isNotBlank() }
                )
            } catch (ex: Exception) {
                _uiState.value = TagsUiState.Error("Error: ${ex.message}")
            }
        }
    }
}