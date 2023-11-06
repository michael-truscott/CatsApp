package com.truscorp.catsapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.truscorp.catsapp.ui.common.CatUi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            delay(2000)
            // TODO: Get actual API result
            _uiState.value = HomeUiState.Success(
                catList = listOf(
                    CatUi("1", listOf("cute", "funny", "orange")),
                    CatUi("2", listOf("chonky")),
                    CatUi("3", listOf())
                )
            )
        }
    }
}