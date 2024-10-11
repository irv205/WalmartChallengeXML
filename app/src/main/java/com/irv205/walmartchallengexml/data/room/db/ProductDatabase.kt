package com.irv205.walmartchallengexml.data.room.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.irv205.walmartchallengexml.data.room.dao.ProductDao
import com.irv205.walmartchallengexml.data.room.entity.ProductEntity

@Database(entities = [ProductEntity::class], version = 1, exportSchema = false)
abstract class ProductDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
}