package com.marcossan.scanner.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.marcossan.scanner.data.database.dao.ProductDao
import com.marcossan.scanner.data.database.entities.ProductEntity

@Database(entities = [ProductEntity::class], version = 1)
abstract class ProductDatabase : RoomDatabase() {
    abstract fun getProductDao() : ProductDao
}