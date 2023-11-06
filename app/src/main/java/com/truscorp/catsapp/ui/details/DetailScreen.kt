package com.truscorp.catsapp.ui.details

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.truscorp.catsapp.ui.common.CatUi
import com.truscorp.catsapp.ui.common.ErrorContent
import com.truscorp.catsapp.ui.common.LoadingContent

@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    viewModel: DetailViewModel
) {
    val uiState by viewModel.uiState.collectAsState()
    DetailScreenStateless(modifier = modifier, uiState = uiState)
}

@Composable
fun DetailScreenStateless(
    modifier: Modifier = Modifier,
    uiState: DetailUiState
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
                DetailScreenContent(cat = uiState.cat)
            }
        }
    }
}

@Composable
fun DetailScreenContent(
    modifier: Modifier = Modifier,
    cat: CatUi
) {
    Column(modifier.fillMaxSize()) {
        SubcomposeAsyncImage(
            modifier = Modifier.fillMaxWidth().height(400.dp),
            model = cat.imageUrl,
            contentDescription = "Probably a picture of a cat",
            contentScale = ContentScale.Fit,
            loading = {
                Box(modifier = Modifier.fillMaxSize()) {
                    CircularProgressIndicator(Modifier.align(Alignment.Center))
                }
            }
        )
    }
}