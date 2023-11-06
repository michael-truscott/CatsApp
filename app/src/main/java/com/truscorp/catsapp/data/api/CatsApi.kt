package com.truscorp.catsapp.data.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

// https://cataas.com/
// Cat as a service
interface CatsApi {

    @GET("api/cats")
    suspend fun cats(
        @Query("tags") tags: String = "",
        @Query("skip") skip: Int = 0,
        @Query("limit") limit: Int = 10
    ): Response<List<CatModel>>

    @GET("api/tags")
    suspend fun tags(): Response<List<String>>
}