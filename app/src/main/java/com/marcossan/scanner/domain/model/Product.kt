package com.marcossan.scanner.domain.model

import com.marcossan.scanner.data.database.entities.ProductEntity
import com.marcossan.scanner.data.network.ProductJson

data class Product(
    val code: String,
    val name: String
)

fun Product.toDomain() = Product(code, name)
fun ProductEntity.toDomain() = Product(code, name)