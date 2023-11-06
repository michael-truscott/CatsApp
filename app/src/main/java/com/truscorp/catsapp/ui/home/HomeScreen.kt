package com.truscorp.catsapp.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.truscorp.catsapp.ui.common.CatUi

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel,
    navController: NavController
) {
    val uiState by viewModel.uiState.collectAsState()
    HomeScreenStateless(modifier, uiState = uiState)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenStateless(
    modifier: Modifier = Modifier,
    uiState: HomeUiState
) {
    Column(modifier = modifier.fillMaxSize()) {
        TopAppBar(title = { Text(text = "Home") })
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
    LazyColumn(modifier = Modifier
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
    Card(modifier.fillMaxWidth().height(200.dp)) {
        // TODO:
    }
}