package com.truscorp.catsapp.data.api

import com.google.gson.annotations.SerializedName

data class CatModel(
    @SerializedName("_id") val id: String,
    @SerializedName("mimetype") val mimeType: String,
    @SerializedName("size") val size: Int,
    @SerializedName("tags") val tags: List<String>
)
