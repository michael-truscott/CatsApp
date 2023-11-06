package com.truscorp.catsapp.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import com.truscorp.catsapp.ui.common.CatUi

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel,
    navController: NavController
) {
    val uiState by viewModel.uiState.collectAsState()
    HomeScreenStateless(modifier, uiState = uiState, onAction = { action ->
        viewModel.performAction(action)
    })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenStateless(
    modifier: Modifier = Modifier,
    uiState: HomeUiState,
    onAction: (HomeUiAction) -> Unit
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
                Box(modifier = Modifier.fillMaxSize()) {
                    CircularProgressIndicator(Modifier.align(Alignment.Center))
                }
            }

            is HomeUiState.Error -> {
                Box(modifier = Modifier.fillMaxSize()) {
                    Text(
                        text = uiState.message, color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }

            is HomeUiState.Success -> {
                CatList(
                    Modifier.fillMaxSize(),
                    cats = uiState.catList
                )
            }
        }
    }
}

@Composable
fun CatList(
    modifier: Modifier = Modifier,
    cats: List<CatUi>
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp)
    ) {
        items(cats, key = { it.id }) { cat ->
            CatListItem(cat = cat)
        }
    }
}

@Composable
fun CatListItem(
    modifier: Modifier = Modifier,
    cat: CatUi
) {
    Card(modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .background(Color.Gray)
                .fillMaxWidth()
                .aspectRatio(1f)
        ) {
            SubcomposeAsyncImage(
                modifier = Modifier.fillMaxSize(),
                model = cat.imageUrl,
                contentDescription = "Probably a picture of a cat",
                contentScale = ContentScale.Crop,
                loading = {
                    Box(modifier = Modifier.fillMaxSize()) {
                        CircularProgressIndicator(Modifier.align(Alignment.Center))
                    }
                }
            )
        }
        if (cat.tags.isNotEmpty()) {
            TagList(tags = cat.tags)
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TagList(
    modifier: Modifier = Modifier,
    tags: List<String>
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            tags.forEach { tag ->
                TagListItem(tag = tag)
            }
        }
    }
}

@Composable
fun TagListItem(
    tag: String
) {
    Box(Modifier.clip(RoundedCornerShape(percent = 50))) {
        Surface(color = MaterialTheme.colorScheme.primary) {
            Text(
                modifier = Modifier
                    .padding(horizontal = 8.dp, vertical = 4.dp)
                    .align(Alignment.Center), text = "#$tag"
            )
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
        )
    )
}