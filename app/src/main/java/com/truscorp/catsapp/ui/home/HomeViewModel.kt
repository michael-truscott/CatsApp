package com.truscorp.catsapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.truscorp.catsapp.ui.common.CatUi
import com.truscorp.catsapp.ui.data.api.CatsApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val catsApi: CatsApi
) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        refresh()
    }

    fun performAction(action: HomeUiAction) {
        when (action) {
            is HomeUiAction.Refresh -> {
                refresh()
            }
        }
    }

    private fun refresh() {
        viewModelScope.launch {
            _uiState.value = HomeUiState.Loading
            delay(2000)
            try {
                val result = catsApi.cats()
                if (!result.isSuccessful) {
                    _uiState.value = HomeUiState.Error("Request failed with status code ${result.code()}")
                }
                val body = result.body()
                if (body == null) {
                    _uiState.value = HomeUiState.Error("A null response was received")
                    return@launch
                }

                _uiState.value = HomeUiState.Success(
                    catList = body.map {
                        CatUi(id = it.id, tags = it.tags)
                    }
                )
            } catch (ex: Exception) {
                _uiState.value = HomeUiState.Error("Error: ${ex.message}")
            }
        }
    }
}