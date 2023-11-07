package com.truscorp.catsapp.ui.common

import com.truscorp.catsapp.data.repositories.cat.Cat

data class CatUi(
    val id: String,
    val imageUrl: String? = null,
    val tags: List<String> = listOf(),
    val isFavourite: Boolean = false,
)

fun Cat.toCatUi(): CatUi {
    return CatUi(
        id = id,
        imageUrl = imageUrl,
        tags = tags.filter { it.isNotEmpty() },
        isFavourite = isFavourite
    )
}