package com.truscorp.catsapp.data.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

// https://cataas.com/
// Cat as a service
interface CatsApi {

    @GET("api/cats")
    suspend fun getAllCats(
        @Query("tags") tags: String = "",
        @Query("skip") skip: Int = 0,
        @Query("limit") limit: Int = 10
    ): Response<List<CatModel>>

    @GET("cat/{id}?json=true")
    suspend fun getCatById(@Path("id") id: String): Response<CatModel>

    @GET("api/tags")
    suspend fun tags(): Response<List<String>>
}