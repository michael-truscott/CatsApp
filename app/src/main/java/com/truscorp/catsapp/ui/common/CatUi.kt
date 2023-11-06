package com.truscorp.catsapp.ui.common

import com.truscorp.catsapp.data.api.CatModel
import com.truscorp.catsapp.data.repositories.Cat

data class CatUi(
    val id: String,
    val imageUrl: String? = null,
    val tags: List<String> = listOf(),
    val isFavourite: Boolean = false,
)

fun CatModel.toCatUi(): CatUi {
    return CatUi(
        id = id,
        imageUrl = "https://cataas.com/cat/${id}",
        tags = tags,
        isFavourite = false
    )
}

fun Cat.toCatUi(): CatUi {
    return CatUi(
        id = id,
        imageUrl = imageUrl,
        tags = tags,
        isFavourite = isFavourite
    )
}