package com.truscorp.catsapp.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage

@Composable
fun CatList(
    modifier: Modifier = Modifier,
    cats: List<CatUi>,
    onCatClicked: (CatUi) -> Unit,
    onFavouriteClicked: (CatUi) -> Unit,
    onTagClicked: (String) -> Unit
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp)
    ) {
        items(cats, key = { it.id }) { cat ->
            CatListItem(
                cat = cat, onClick = { onCatClicked(cat) },
                onFavouriteClicked = { onFavouriteClicked(cat) },
                onTagClicked = onTagClicked
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatListItem(
    modifier: Modifier = Modifier,
    cat: CatUi,
    onClick: () -> Unit,
    onFavouriteClicked: (CatUi) -> Unit,
    onTagClicked: (String) -> Unit
) {
    Card(modifier = modifier.fillMaxWidth(), onClick = onClick) {
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
            FavouriteButton(
                modifier = Modifier.align(Alignment.TopEnd),
                selected = cat.isFavourite
            ) {
                onFavouriteClicked(cat)
            }
        }
        if (cat.tags.isNotEmpty()) {
            CatTagList(tags = cat.tags, onTagClicked = onTagClicked)
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CatTagList(
    modifier: Modifier = Modifier,
    tags: List<String>,
    onTagClicked: (String) -> Unit
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
                TagListItem(tag = tag, onClick = { onTagClicked(tag) })
            }
        }
    }
}

@Composable
private fun TagListItem(
    tag: String,
    onClick: () -> Unit
) {
    Box(
        Modifier
            .clip(RoundedCornerShape(percent = 50))
            .clickable(onClick = onClick)
    ) {
        Surface(color = MaterialTheme.colorScheme.primary) {
            Text(
                modifier = Modifier
                    .padding(horizontal = 8.dp, vertical = 4.dp)
                    .align(Alignment.Center), text = if (tag.startsWith('#')) tag else "#$tag"
            )
        }
    }
}