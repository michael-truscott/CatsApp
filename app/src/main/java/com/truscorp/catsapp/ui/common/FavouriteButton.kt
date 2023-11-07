package com.truscorp.catsapp.ui.common

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun FavouriteButton(
    modifier: Modifier = Modifier,
    selected: Boolean,
    onClick: () -> Unit
) {
    IconButton(modifier = modifier, onClick = onClick) {
        if (selected) {
            Icon(imageVector = Icons.Default.Favorite, contentDescription = "Unfavourite", tint = Color.Red)
        } else {
            Icon(imageVector = Icons.Default.FavoriteBorder, contentDescription = "Favourite", tint = Color.White)
        }
    }
}