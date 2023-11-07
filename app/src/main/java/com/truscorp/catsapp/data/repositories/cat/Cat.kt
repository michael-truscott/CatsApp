package com.truscorp.catsapp.data.repositories.cat

data class Cat(
    val id: String,
    val imageUrl: String? = null,
    val tags: List<String> = listOf(),
    val isFavourite: Boolean = false
)
