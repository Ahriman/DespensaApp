package com.marcossan.scanner.data

import com.marcossan.scanner.data.database.dao.ProductDao
import com.marcossan.scanner.data.database.entities.ProductEntity
import com.marcossan.scanner.data.network.ProductApiService
import com.marcossan.scanner.domain.model.Product
import com.marcossan.scanner.domain.model.toDomain
import javax.inject.Inject

class ProductRepository @Inject constructor(
    private val api: ProductApiService,
    private val productDao: ProductDao
) {

    //    suspend fun getAllProductsFromApi(): List<Product> {
//        val response: ProductJson = api.getProduct()
//
//        return response.map { it.toDomain() }
//    }
    suspend fun getProductFromApi(barcode: String): Product {
        val response: Product = api.getProductDomain(barcode = barcode)

        return response.toDomain()
    }

    suspend fun getProductFromDatabase(barcode: String): Product {
        val response: ProductEntity = productDao.get(barcode)

        return response.toDomain()
    }

//    suspend fun getAllProductsFromDatabase(): List<Product> {
//        val response = api.getProduct()
//
//        return response
//    }

    suspend fun insertProduct(productEntity: ProductEntity) {
        productDao.insert(productEntity)
    }

    suspend fun insertProducts(products: List<ProductEntity>) {
        productDao.insertAll(products)
    }

    suspend fun clearProducts() {
        productDao.deleteAll()
    }

}