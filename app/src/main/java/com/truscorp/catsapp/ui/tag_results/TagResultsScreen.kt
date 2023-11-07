package com.truscorp.catsapp.ui.tag_results

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TagResultsScreen(
    modifier: Modifier = Modifier,
    viewModel: TagResultsViewModel,
    navController: NavController
) {
    Column(modifier.fillMaxSize()) {
        TopAppBar(title = { Text("#${viewModel.tag}") })
    }
}