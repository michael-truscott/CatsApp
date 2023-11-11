package com.truscorp.catsapp.ui.tags

import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toLowerCase
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.truscorp.catsapp.data.api.CatsApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TagsViewModel @Inject constructor(
    private val catsApi: CatsApi
) : ViewModel() {

    private val fetchedTags: MutableStateFlow<List<String>?> = MutableStateFlow(null)
    private val searchText = MutableStateFlow("")
    private val errorMessage: MutableStateFlow<String?> = MutableStateFlow(null)

    val uiState = combine(
        fetchedTags,
        searchText,
        errorMessage
    ) { tags, searchText, error ->
        when {
            error != null -> TagsUiState.Error(error)
            tags == null -> TagsUiState.Loading
            else -> {
                val filteredTags = if (searchText.isEmpty()) {
                    tags
                } else {
                    tags.filter { it.contains(searchText, ignoreCase = true) }
                }
                TagsUiState.Success(tags = filteredTags, searchText = searchText, totalTagCount = tags.size)
            }
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), TagsUiState.Loading)

    init {
        refresh()
    }

    private fun refresh() {
        fetchedTags.value = null
        searchText.value = ""
        errorMessage.value = null
        viewModelScope.launch {
            try {
                val result = catsApi.tags()
                if (!result.isSuccessful) {
                    errorMessage.value = "Request failed with status code ${result.code()}"
                    return@launch
                }
                val body = result.body()
                if (body == null) {
                    errorMessage.value = "A null response was received"
                    return@launch
                }

                fetchedTags.value = body
                    .filter { it.isNotBlank() }
                    .sortedBy { (if (it.startsWith("#")) it.drop(1) else it).toLowerCase(Locale.current) }
            } catch (ex: Exception) {
                errorMessage.value = "Error: ${ex.message}"
            }
        }
    }

    fun performAction(action: TagsScreenAction) {
        when (action) {
            is TagsScreenAction.Refresh -> refresh()
            is TagsScreenAction.SetSearchText -> searchText.value = action.text
        }
    }
}

sealed interface TagsScreenAction {
    object Refresh : TagsScreenAction
    data class SetSearchText(val text: String) : TagsScreenAction
}