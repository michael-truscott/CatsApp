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
        viewModelScope.launch {
            val result = catsApi.cats()
        }

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