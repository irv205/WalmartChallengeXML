package com.irv205.walmartchallengexml.data.mapper

import com.irv205.walmartchallengexml.data.room.entity.ProductEntity
import com.irv205.walmartchallengexml.data.model.ProductsDTO
import com.irv205.walmartchallengexml.domain.model.Product

// Mapper from DTO to Entity (for storing in Room)
fun ProductsDTO.toEntity(): ProductEntity {
    return ProductEntity(id = this.id, name = this.name, price = this.price)
}

// Mapper from Entity to Domain Model (for use in the domain layer)
fun ProductEntity.toDomainModel(): Product {
    return Product(id = this.id, name = this.name, price = this.price)
}