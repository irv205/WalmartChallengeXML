package com.irv205.walmartchallengexml.data.repository

import com.irv205.walmartchallengexml.core.util.ResponseHandler
import com.irv205.walmartchallengexml.data.room.dao.ProductDao
import com.irv205.walmartchallengexml.data.mapper.toDomainModel
import com.irv205.walmartchallengexml.data.mapper.toEntity
import com.irv205.walmartchallengexml.data.service.ApiService
import com.irv205.walmartchallengexml.domain.model.Product
import com.irv205.walmartchallengexml.domain.repository.IRepository
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val service: ApiService,
    private val productDao: ProductDao
) : IRepository {

    override suspend fun getProducts(): ResponseHandler<List<Product>> {
        return try {
            val response = service.getProducts()

            val productEntities = response.products.map { dto -> dto.toEntity() }
            productDao.insertAll(productEntities)

            val localProducts = productDao.getAllProducts()
                .map { entities ->
                    entities.map { entity -> entity.toDomainModel() }
                }

            ResponseHandler.Success(localProducts.first())

        } catch (e: Exception) {
            return try {
                val localProducts = productDao.getAllProducts()
                    .map { entities ->
                        entities.map { entity -> entity.toDomainModel() }
                    }
                ResponseHandler.Success(localProducts.first())

            } catch (dbException: Exception) {
                ResponseHandler.Error(message = "Error accessing data: ${dbException.message}")
            }
        }
    }
}