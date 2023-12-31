package com.truscorp.catsapp.ui.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.truscorp.catsapp.ui.common.CatTagList
import com.truscorp.catsapp.ui.common.CatUi
import com.truscorp.catsapp.ui.common.ErrorContent
import com.truscorp.catsapp.ui.common.FavouriteButton
import com.truscorp.catsapp.ui.common.LoadingContent

@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    viewModel: DetailViewModel,
    onBackClicked: () -> Unit,
    onTagClicked: (String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    DetailScreenStateless(
        modifier = modifier, uiState = uiState,
        onAction = { viewModel.performAction(it) },
        onBackClicked = onBackClicked,
        onTagClicked = onTagClicked
    )
}

@Composable
fun DetailScreenStateless(
    modifier: Modifier = Modifier,
    uiState: DetailUiState,
    onAction: (DetailUiAction) -> Unit,
    onBackClicked: () -> Unit,
    onTagClicked: (String) -> Unit
) {
    Column(modifier = modifier.fillMaxSize()) {
        when (uiState) {
            is DetailUiState.Loading -> {
                LoadingContent()
            }

            is DetailUiState.Error -> {
                ErrorContent(message = uiState.message)
            }

            is DetailUiState.Success -> {
                DetailScreenContent(
                    cat = uiState.cat,
                    onAction = onAction,
                    onBackClicked = onBackClicked,
                    onTagClicked = onTagClicked
                )
            }
        }
    }
}

@Composable
private fun DetailScreenContent(
    modifier: Modifier = Modifier,
    cat: CatUi,
    onAction: (DetailUiAction) -> Unit,
    onBackClicked: () -> Unit,
    onTagClicked: (String) -> Unit
) {
    Column(modifier.fillMaxSize()) {
        Box(modifier = Modifier.background(Color.Black)) {
            SubcomposeAsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp),
                model = cat.imageUrl,
                contentDescription = "Probably a picture of a cat",
                contentScale = ContentScale.Fit,
                loading = {
                    Box(modifier = Modifier.fillMaxSize()) {
                        CircularProgressIndicator(Modifier.align(Alignment.Center))
                    }
                }
            )
            IconButton(
                modifier = Modifier.align(Alignment.TopStart),
                onClick = onBackClicked
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White
                )
            }
            FavouriteButton(
                modifier = Modifier.align(Alignment.TopEnd),
                selected = cat.isFavourite
            ) {
                onAction(DetailUiAction.SetFavourite(!cat.isFavourite))
            }
        }
        DetailScreenTagList(tags = cat.tags, onTagClicked = onTagClicked)
    }
}

@Composable
fun DetailScreenTagList(
    modifier: Modifier = Modifier,
    tags: List<String>,
    onTagClicked: (String) -> Unit
) {
    Column(modifier.padding(8.dp)) {
        Text(text = "Tags", style = MaterialTheme.typography.titleLarge)
        if (tags.isEmpty()) {
            Text(text = "No tags")
        } else {
            CatTagList(tags = tags, onTagClicked = onTagClicked)
        }
    }
}