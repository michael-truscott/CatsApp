package com.truscorp.catsapp.ui.tags

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.truscorp.catsapp.ui.common.ErrorContent
import com.truscorp.catsapp.ui.common.LoadingContent

@Composable
fun TagsScreen(
    modifier: Modifier = Modifier,
    viewModel: TagsViewModel,
    navController: NavController
) {
    val uiState by viewModel.uiState.collectAsState()
    TagsScreenStateless(
        uiState = uiState,
        onTagClicked = { } // TODO
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TagsScreenStateless(
    modifier: Modifier = Modifier,
    uiState: TagsUiState,
    onTagClicked: (String) -> Unit
) {
    Column(modifier) {
        TopAppBar(title = { Text("Tags") })
        when (uiState) {
            is TagsUiState.Loading -> {
                LoadingContent()
            }

            is TagsUiState.Error -> {
                ErrorContent(message = uiState.message)
            }

            is TagsUiState.Success -> {
                TagList(
                    modifier = Modifier.fillMaxSize(),
                    tags = uiState.tags,
                    onTagClicked = onTagClicked
                )
            }
        }
    }
}

@Composable
private fun TagList(
    modifier: Modifier = Modifier,
    tags: List<String>,
    onTagClicked: (String) -> Unit
) {
    LazyColumn(modifier, verticalArrangement = Arrangement.spacedBy(2.dp)) {
        item {
            Text(text = "${tags.size} tags", modifier = Modifier.padding(8.dp))
        }
        items(tags, key = { it }) { tag ->
            TagListItem(tag = tag)
        }
    }
}

@Composable
private fun TagListItem(
    tag: String,
    modifier: Modifier = Modifier
) {
    Card(modifier.fillMaxWidth()) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = if (tag.startsWith('#')) tag else "#$tag")
            Icon(imageVector = Icons.Default.KeyboardArrowRight, contentDescription = null)
        }
    }
}

@Preview
@Composable
fun TagListPreview() {
    TagList(
        Modifier.fillMaxSize(),
        tags = listOf("asdf", "rthyr", "swag", "yolo", "chonk", "silly", "eepy"),
        onTagClicked = {}
    )
}