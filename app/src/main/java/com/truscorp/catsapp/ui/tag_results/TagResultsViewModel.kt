package com.truscorp.catsapp.ui.tag_results

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.truscorp.catsapp.data.repositories.cat.CatRepository
import com.truscorp.catsapp.ui.common.toCatUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TagResultsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val catRepository: CatRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<TagResultsUiState>(TagResultsUiState.Loading)
    val uiState = _uiState.asStateFlow()

    val tag = requireNotNull(savedStateHandle.get<String>("tag"))

    init {
        _uiState.value = TagResultsUiState.Loading

        viewModelScope.launch {
            catRepository.getAll(listOf(tag)).collect { list ->
                _uiState.value = TagResultsUiState.Success(
                    catList = list.map { it.toCatUi() }
                )
            }
        }
    }

    fun setFavourite(id: String, favourite: Boolean) {
        viewModelScope.launch {
            catRepository.setFavourite(id, favourite)
        }
    }
}