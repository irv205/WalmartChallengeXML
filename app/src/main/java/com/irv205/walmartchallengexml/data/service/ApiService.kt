package com.irv205.walmartchallengexml.data.service

import com.irv205.walmartchallengexml.data.model.ProductsResponse
import retrofit2.http.GET

interface ApiService {

    @GET("products")
    suspend fun getProducts(): ProductsResponse
}