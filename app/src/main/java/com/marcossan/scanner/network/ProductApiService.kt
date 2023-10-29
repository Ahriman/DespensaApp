package com.marcossan.scanner.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path

private const val COUNTRY_CODE = "es"
private const val BASE_URL = "https://$COUNTRY_CODE.openfoodfacts.org/api/v0/"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
    .baseUrl(BASE_URL)
    .build()

interface ProductApiService {

    @GET("product/{barcode}.json")
//    suspend fun getPhotos(): List<MarsPhoto>
    suspend fun getProduct(
        @Path("barcode")
        barcode: String
    ): Product
//    suspend fun getPhotos(): String


    object MarsApi {
        val retrofitService: ProductApiService by lazy {
            retrofit.create(ProductApiService::class.java)
        }
    }

}