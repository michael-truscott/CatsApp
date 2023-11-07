package com.truscorp.catsapp.ui.favourites

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil.compose.SubcomposeAsyncImage
import com.truscorp.catsapp.data.repositories.favourite.Favourite
import com.truscorp.catsapp.ui.common.ErrorContent
import com.truscorp.catsapp.ui.common.LoadingContent

@Composable
fun FavouritesScreen(
    modifier: Modifier = Modifier,
    viewModel: FavouritesViewModel,
    onItemClick: (Favourite) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    FavouritesScreenStateless(
        modifier = modifier,
        uiState = uiState,
        onItemClick = onItemClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavouritesScreenStateless(
    modifier: Modifier = Modifier,
    uiState: FavouritesUiState,
    onItemClick: (Favourite) -> Unit
) {
    Column(modifier.fillMaxSize()) {
        TopAppBar(title = { Text(text = "Favourites") })

        when (uiState) {
            is FavouritesUiState.Loading -> {
                LoadingContent()
            }

            is FavouritesUiState.Error -> {
                ErrorContent(message = uiState.message)
            }

            is FavouritesUiState.Success -> {
                FavouritesScreenContent(favourites = uiState.favourites, onItemClick = onItemClick)
            }
        }
    }
}

@Composable
fun FavouritesScreenContent(
    modifier: Modifier = Modifier,
    favourites: List<Favourite>,
    onItemClick: (Favourite) -> Unit
) {
    LazyVerticalGrid(modifier = modifier, columns = GridCells.Fixed(2)) {
        items(favourites, key = { it.id }) { item ->
            FavouritesGridItem(Modifier.aspectRatio(1f), favourite = item) {
                onItemClick(item)
            }
        }
    }
}

@Composable
fun FavouritesGridItem(
    modifier: Modifier = Modifier,
    favourite: Favourite,
    onClick: () -> Unit
) {
    Box(modifier = modifier.clickable(onClick = onClick)) {
        SubcomposeAsyncImage(
            modifier = Modifier.fillMaxSize(),
            model = favourite.imageUrl,
            contentDescription = "Probably a picture of a cat",
            contentScale = ContentScale.Crop,
            loading = {
                Box(modifier = Modifier.fillMaxSize()) {
                    CircularProgressIndicator(Modifier.align(Alignment.Center))
                }
            }
        )
    }
}