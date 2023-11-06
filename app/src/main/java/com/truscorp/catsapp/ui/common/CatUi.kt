package com.truscorp.catsapp.ui.common

import com.truscorp.catsapp.data.api.CatModel

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