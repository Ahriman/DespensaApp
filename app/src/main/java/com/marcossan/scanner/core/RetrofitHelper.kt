package com.marcossan.scanner.core

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// TODO: Eliminar
private const val COUNTRY_CODE = "es"
private const val BASE_URL = "https://$COUNTRY_CODE.openfoodfacts.org/api/v0/"
object RetrofitHelper {
    fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}