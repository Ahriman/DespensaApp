package com.marcossan.scanner.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

@Serializable
data class Product (
    val code: String,
//    @SerialName(value = "img_src")
//    val imgSrc: String
    val product: JsonObject,
    val status: Int,
    @SerialName(value = "status_verbose")
    val statusVerbose: String
)