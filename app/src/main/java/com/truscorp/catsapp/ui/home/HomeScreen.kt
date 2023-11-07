package com.truscorp.catsapp.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.truscorp.catsapp.ui.common.CatList
import com.truscorp.catsapp.ui.common.CatListItem
import com.truscorp.catsapp.ui.common.CatUi
import com.truscorp.catsapp.ui.common.ErrorContent
import com.truscorp.catsapp.ui.common.LoadingContent

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel,
    onCatClicked: (CatUi) -> Unit,
    onTagClicked: (String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    HomeScreenStateless(modifier,
        uiState = uiState,
        onAction = { action ->
            viewModel.performAction(action)
        },
        onCatClicked = onCatClicked,
        onTagClicked = onTagClicked
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenStateless(
    modifier: Modifier = Modifier,
    uiState: HomeUiState,
    onAction: (HomeUiAction) -> Unit,
    onCatClicked: (CatUi) -> Unit,
    onTagClicked: (String) -> Unit
) {
    Column(modifier = modifier.fillMaxSize()) {
        TopAppBar(title = { Text(text = "Home") }, actions = {
            IconButton(
                onClick = { onAction(HomeUiAction.Refresh) },
                enabled = uiState != HomeUiState.Loading
            ) {
                Icon(imageVector = Icons.Default.Refresh, contentDescription = "Refresh")
            }
        })
        when (uiState) {
            is HomeUiState.Loading -> {
                LoadingContent()
            }

            is HomeUiState.Error -> {
                ErrorContent(message = uiState.message)
            }

            is HomeUiState.Success -> {
                CatList(
                    Modifier.fillMaxSize(),
                    cats = uiState.catList,
                    onFavouriteClicked = { cat ->
                        onAction(HomeUiAction.SetFavourite(cat.id, cat.isFavourite))
                    },
                    onCatClicked = onCatClicked,
                    onTagClicked = onTagClicked
                )
            }
        }
    }
}

@Preview
@Composable
fun CatListItemPreview() {
    CatListItem(
        cat = CatUi(
            id = "1",
            tags = listOf(
                "asdf",
                "lorem",
                "ipsum",
                "123465",
                "blah",
                "hello",
                "long tag to extend",
                "more",
                "tags"
            ),
        ),
        onClick = {},
        onFavouriteClicked = {},
        onTagClicked = {}
    )
}