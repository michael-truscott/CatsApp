package com.truscorp.catsapp.ui.tag_results

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.truscorp.catsapp.ui.common.CatList
import com.truscorp.catsapp.ui.common.CatUi
import com.truscorp.catsapp.ui.common.ErrorContent
import com.truscorp.catsapp.ui.common.LoadingContent

@Composable
fun TagResultsScreen(
    modifier: Modifier = Modifier,
    viewModel: TagResultsViewModel,
    navController: NavController
) {
    val uiState by viewModel.uiState.collectAsState()
    TagResultsScreenStateless(
        uiState = uiState, tag = viewModel.tag,
        onFavouriteClicked = { cat -> viewModel.setFavourite(cat.id, !cat.isFavourite) },
        onCatClicked = { cat -> } // TODO:
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TagResultsScreenStateless(
    modifier: Modifier = Modifier,
    uiState: TagResultsUiState,
    tag: String,
    onFavouriteClicked: (CatUi) -> Unit,
    onCatClicked: (CatUi) -> Unit
) {
    Column(modifier = modifier.fillMaxSize()) {
        TopAppBar(title = { Text("#$tag") })
        when (uiState) {
            is TagResultsUiState.Loading -> {
                LoadingContent()
            }

            is TagResultsUiState.Error -> {
                ErrorContent(message = uiState.message)
            }

            is TagResultsUiState.Success -> {
                CatList(
                    Modifier.fillMaxSize(),
                    cats = uiState.catList,
                    onFavouriteClicked = onFavouriteClicked,
                    onCatClicked = onCatClicked
                )
            }
        }
    }
}