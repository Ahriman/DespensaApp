/*
 * Copyright (C) 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.marcossan.scanner.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.stream.JsonReader
import com.marcossan.scanner.data.model.Product
import com.marcossan.scanner.data.model.ProductList
import com.marcossan.scanner.data.network.ProductApiService
import com.marcossan.scanner.data.network.ProductJson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.serialization.MissingFieldException
import java.io.StringReader
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

sealed interface ScannerUiState {
    data class Success(val product: Product) : ScannerUiState
    object Error : ScannerUiState
    object Loading : ScannerUiState
}

@HiltViewModel
class ProductViewModel @Inject constructor(): ViewModel() {

    private var _barcode by mutableStateOf("")
    val barcode get() = _barcode

    private var _productName by mutableStateOf("")
    val productName get() = _productName

    private var _productUrl by mutableStateOf("")
    val productUrl get() = _productUrl

    private var _productExpireDate by mutableStateOf("")
    val productExpireDate get() = _productExpireDate

    private var _productDateAdded by mutableStateOf("")
    val productDateAdded get() = _productDateAdded

    private var _productQuantity by mutableStateOf("")
    val productQuantity get() = _productQuantity

    //
    private var _product by mutableStateOf(
        Product(
            code = _barcode,
            name = _productName,
            imageUrl = _productUrl,
            expirationDate = _productExpireDate,
            quantity = _productQuantity
        )
    )
    val product get() = _product


    private var _isBarcodeScanned by mutableStateOf(false)
    val isBarcodeScanned get() = _isBarcodeScanned

    fun setIsBarcodeScanned(enable: Boolean) {
        _isBarcodeScanned = enable
    }

    fun setProductDateAdded(date: String) {
        _productDateAdded = date
    }

    fun onScannedBarcode(barcode: String) {
        _barcode = barcode
        _isBarcodeScanned = true

//        suspend {
//            getProductFromApi(_barcode)
//        }
//        _product = _product.copy(code = barcode)

//        getProduct(_barcode)
//
//        when (scannerUiState) {
//            is ScannerUiState.Success -> {
//                _productName = (scannerUiState as ScannerUiState.Success).product.name
//                _productName = (scannerUiState as ScannerUiState.Success).product.name
//            }
//
//            else -> {}
//        }
//        _productName = scannerUiState
    }

    fun onGetProduct(barcode: String) {

    }

    fun onBarcodeChange(barcode: String) {
        _barcode = barcode
    }

    fun onProductNameChange(productName: String) {
        _productName = productName.replaceFirstChar { it.uppercase() }
    }

    fun onProductExpireDateChange(productExpireDate: String) {
        _productExpireDate = productExpireDate
    }

    fun onProductQuantityChange(productQuantity: String) {
        _productQuantity = productQuantity
    }

    val listaProductos = ProductList().list

    /** The mutable State that stores the status of the most recent request */
    var scannerUiState: ScannerUiState by mutableStateOf(ScannerUiState.Loading)
        private set

    suspend fun getProductFromApi(barcode: String) {
        val productJson: ProductJson
        try {
            productJson= ProductApiService.OpenFoodFactsApi.retrofitService.getProduct(
                barcode = barcode ?: _barcode
            )
            val product = getProductFromProductJson(productJson)
            _productName = product.name
            _productUrl = product.imageUrl
        } catch (e: MissingFieldException) {
            // TODO: Crear ventana modal, emergente?
            println("El código de barras no es válido o no existe en la base de datos porque el Json devuelto no es correcto.")
        }
    }

    suspend fun addProduct(barcode: String) {
        val productJson: ProductJson = ProductApiService.OpenFoodFactsApi.retrofitService.getProduct(
            barcode = barcode ?: _barcode
        )
        listaProductos.add(getProductFromProductJson(productJson))
    }

    /**
     * Gets Product information from the Open Food Fatcs API Retrofit service and updates the
     * [Product] [List] [MutableList].
     */
    fun getProduct(barcode: String?) {

        viewModelScope.launch {
            scannerUiState =
                try {
                    val productJson: ProductJson =
                        ProductApiService.OpenFoodFactsApi.retrofitService.getProduct(
                            barcode = barcode ?: _barcode
                        )
                    _product = getProductFromProductJson(productJson = productJson)
//                    listaProductos.add(product)
//                    if (barcode.isNullOrEmpty()) {
//                        listaProductos.add(product)
//                    }


                    ScannerUiState.Success(
                        _product
                    )
                } catch (e: Exception) {
                    e.printStackTrace()

                    ScannerUiState.Error
                }

        }

    }

    private fun getProductFromProductJson(productJson: ProductJson): Product {
        val barcode = _barcode //?: product.code
        var nombreProducto = ""
        var imageURL = ""
        var fechaCaducidad = ""

        val stringReader = StringReader(productJson.product.toString())
        val jsonReader = JsonReader(stringReader)

        jsonReader.beginObject() // Start reading the JSON object
        while (jsonReader.hasNext()) {
            when (jsonReader.nextName()) {
                "product_name_es" -> {
                    val value = jsonReader.nextString()
                    println("product_name_es: $value")
                    nombreProducto = value
                }

                "image_front_url" -> {
                    val value = jsonReader.nextString()
                    println("image_front_url: $value")
                    imageURL = value
                }

                "expiration_date" -> {
                    val value = jsonReader.nextString()
                    println("Fecha de caducidad: $value")
                    fechaCaducidad = value
                    if (value.isEmpty()) fechaCaducidad = "No disponible"
                }

                else -> jsonReader.skipValue() // Handle unexpected fields
            }
        }
        jsonReader.endObject() // End of the JSON object

        jsonReader.close() // Close the JsonReader when done

        return Product(
            code = barcode,
            name = nombreProducto,
            imageUrl = imageURL,
//            expirationDate = fechaCaducidad,
            expirationDate = _productExpireDate,
            dateAdded = calcularFechaActual()
        )
    }


    private fun calcularFechaActual(): String {
        val fecha = DateTimeFormatter.ofPattern("dd/MM/yy HH:mm:ss")
//    println("dd/MM/yy HH:mm:ss: " + fecha.format(LocalDateTime.now()))
        return fecha.format(LocalDateTime.now())
    }


}



