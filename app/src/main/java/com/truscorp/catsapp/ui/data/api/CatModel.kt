package com.truscorp.catsapp.ui.data.api

import com.google.gson.annotations.SerializedName

data class CatModel(
    @SerializedName("_id") val id: String,
    @SerializedName("mimetype") val mimeType: String,
    @SerializedName("size") val size: Int,
    @SerializedName("tags") val tags: List<String>
)
