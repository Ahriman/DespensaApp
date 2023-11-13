package com.marcossan.scanner.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.marcossan.scanner.data.database.entities.ProductEntity
import com.marcossan.scanner.data.database.utils.Constants

@Dao
interface ProductDao {

    @Query("SELECT * FROM ${Constants.products_table_name} WHERE code = :barcode")
    suspend fun get(barcode: String): ProductEntity

    @Query("SELECT * FROM ${Constants.products_table_name}")
    fun getAll(): List<ProductEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(productEntity: ProductEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(products: List<ProductEntity>)

    @Query("DELETE FROM ${Constants.products_table_name}")
    suspend fun deleteAll()

    @Delete
    fun delete(productEntity: ProductEntity)

    @Update
    fun update(productEntity: ProductEntity)

}