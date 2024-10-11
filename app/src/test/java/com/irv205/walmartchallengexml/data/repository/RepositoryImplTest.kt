package com.irv205.walmartchallengexml.data.repository

import com.irv205.walmartchallengexml.core.util.ResponseHandler
import com.irv205.walmartchallengexml.data.mapper.toDomainModel
import com.irv205.walmartchallengexml.data.model.ProductsDTO
import com.irv205.walmartchallengexml.data.model.ProductsResponse
import com.irv205.walmartchallengexml.data.room.dao.ProductDao
import com.irv205.walmartchallengexml.data.room.entity.ProductEntity
import com.irv205.walmartchallengexml.data.service.ApiService
import com.irv205.walmartchallengexml.domain.model.Product
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class RepositoryImplTest {

    private val apiService: ApiService = mockk()
    private val productDao: ProductDao = mockk()

    private lateinit var repository: RepositoryImpl

    @Before
    fun setUp() {
        repository = RepositoryImpl(apiService, productDao)
    }

    @Test
    fun `test getProducts success from API and store in DB`() = runTest {
        val productsDTO = listOf(
            ProductsDTO(1, "Product 1", 10.0f),
            ProductsDTO(2, "Product 2", 20.0f)
        )
        val response = ProductsResponse(productsDTO)
        coEvery { apiService.getProducts() } returns response

        coEvery { productDao.insertAll(any()) } returns Unit

        val localProducts = listOf(
            ProductEntity(1, "Product 1", 10.0f),
            ProductEntity(2, "Product 2", 20.0f)
        )
        coEvery { productDao.getAllProducts() } returns kotlinx.coroutines.flow.flowOf(localProducts)

        val result = repository.getProducts()

        assertEquals(ResponseHandler.Success(localProducts.map { it.toDomainModel() }), result)

        coVerify { apiService.getProducts() }
        coVerify { productDao.insertAll(any()) }
        coVerify { productDao.getAllProducts() }
    }

    @Test
    fun `test getProducts error from API and success from DB`() = runTest {

        coEvery { apiService.getProducts() } throws Exception("API Error")

        val localProducts = listOf(
            ProductEntity(1, "Product 1", 10.0f),
            ProductEntity(2, "Product 2", 20.0f)
        )
        coEvery { productDao.getAllProducts() } returns kotlinx.coroutines.flow.flowOf(localProducts)

        val result = repository.getProducts()

        assertEquals(ResponseHandler.Success(localProducts.map { it.toDomainModel() }), result)

        coVerify { productDao.getAllProducts() }
    }

    @Test
    fun `test getProducts error from API and DB`() = runTest {

        coEvery { apiService.getProducts() } throws Exception("API Error")

        coEvery { productDao.getAllProducts() } throws Exception("Database Error")

        val result = repository.getProducts()

        assert(result is ResponseHandler.Error)
        assertEquals("Error accessing data: Database Error", (result as ResponseHandler.Error).message)

        coVerify { apiService.getProducts() }
        coVerify { productDao.getAllProducts() }
    }

    @Test
    fun `test getProducts API returns empty list`() = runTest {

        val response = ProductsResponse(emptyList())
        coEvery { apiService.getProducts() } returns response

        coEvery { productDao.insertAll(emptyList()) } returns Unit

        coEvery { productDao.getAllProducts() } returns kotlinx.coroutines.flow.flowOf(emptyList())

        val result = repository.getProducts()

        assertEquals(ResponseHandler.Success(emptyList<Product>()), result)

        coVerify { apiService.getProducts() }
        coVerify { productDao.insertAll(emptyList()) }
        coVerify { productDao.getAllProducts() }
    }
}