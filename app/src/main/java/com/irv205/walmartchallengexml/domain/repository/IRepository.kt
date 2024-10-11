package com.irv205.walmartchallengexml.domain.repository

import com.irv205.walmartchallengexml.core.util.ResponseHandler
import com.irv205.walmartchallengexml.domain.model.Product

interface IRepository {
    suspend fun getProducts(): ResponseHandler<List<Product>>
}