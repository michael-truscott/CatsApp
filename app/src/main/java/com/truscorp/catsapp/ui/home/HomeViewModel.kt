package com.truscorp.catsapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.truscorp.catsapp.data.repositories.cat.CatRepository
import com.truscorp.catsapp.ui.common.toCatUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val catRepository: CatRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private var refreshJob: Job? = null

    init {
        refresh()
    }

    fun performAction(action: HomeUiAction) {
        when (action) {
            is HomeUiAction.Refresh -> refresh()
            is HomeUiAction.SetFavourite -> setFavourite(action.id, !action.favourite)
        }
    }

    private fun setFavourite(id: String, favourite: Boolean) {
        viewModelScope.launch {
            catRepository.setFavourite(id, favourite)
        }
    }

    private fun refresh() {
        refreshJob?.cancel()

        _uiState.value = HomeUiState.Loading
        refreshJob = viewModelScope.launch {
            catRepository.getAll().cancellable().collect { list ->
                _uiState.value = HomeUiState.Success(list.map { it.toCatUi() })
            }
        }
    }
}