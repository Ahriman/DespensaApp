package com.marcossan.scanner.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Product(
    val code: String,
    var name: String,
    val imageUrl: String,
    val expirationDate: String = "No disponible",
    val dateAdded: String = "",
    val quantity: String = "1"
)

