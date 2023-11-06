package com.truscorp.catsapp.ui.common

data class CatUi(
    val id: String,
    val tags: List<String> = listOf(),
    val isFavourite: Boolean = false,
)
