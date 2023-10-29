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
package com.marcossan.scanner.ui.screens

import android.app.Application
import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning
import com.marcossan.scanner.BarcodeScanner
import com.marcossan.scanner.network.ProductApiService
import com.marcossan.scanner.network.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.IOException

sealed interface MarsUiState {
    data class Success(val photos: Product) : MarsUiState
    object Error : MarsUiState
    object Loading : MarsUiState
}

class ProductViewModel(application: Application) : AndroidViewModel(application = application) {

    lateinit var barcodeScanner: BarcodeScanner

//    var barcodeScanner = BarcodeScanner(application) // Instantiate your BarcodeScanner here

    val barCodeResults = MutableStateFlow<String?>(null)
//
//    private val _barcodeResult = MutableStateFlow<String?>(null)
//    val barcodeResult = _barcodeResult.asStateFlow()
//
//    init {
//        viewModelScope.launch {
//            barcodeScanner.barCodeResults.collect { result ->
//                _barcodeResult.value = result
//            }
//        }
//    }

    /** The mutable State that stores the status of the most recent request */
    var marsUiState: MarsUiState by mutableStateOf(MarsUiState.Loading)
        private set

//    var barcode: String by mutableStateOf("8424818268292")
//        private set



//    var barcodeResults by mutableStateOf(barCodeResults.value) //"8424818268292"
//        private set


    private val options = GmsBarcodeScannerOptions.Builder()
        .setBarcodeFormats(Barcode.FORMAT_ALL_FORMATS)
        .build()

    private val scanner = GmsBarcodeScanning.getClient(application, options)

    suspend fun startScan() {
        try {
            scanner.startScan()
                .addOnSuccessListener { barcode ->
                    // Task completed successfully
                    barCodeResults.value = barcode.displayValue
                }
                .addOnCanceledListener {
                    // Task cancelled
                    barCodeResults.value = "canceled"
                }
                .addOnFailureListener {
                    // Task failed with an exception
                    barCodeResults.value = "failed"
                }
        } catch (e: Exception) {

        }
    }


    /**
     * Call getMarsPhotos() on init so we can display status immediately.
     */
    init {
//        getMarsPhotos()
    }

    /**
     * Gets Mars photos information from the Mars API Retrofit service and updates the
     * [MarsPhoto] [List] [MutableList].
     */
    fun getMarsPhotos() {
        viewModelScope.launch {
            marsUiState = try {
//                val listResult = ProductApiService.MarsApi.retrofitService.getProduct(barcodeResults)
                val test = barCodeResults.value
                val listResult = ProductApiService.MarsApi.retrofitService.getProduct(barcode = barCodeResults.value ?: "8424818268292")

//                MarsUiState.Success("Success. ${listResult.size} Mars photos retrieved")
//                MarsUiState.Success("Success: code: ${listResult.code}" +
//                        "\nMarca: ${listResult.product.getValue("brands")}" +
//                        "\nNombre del producto: ${listResult.product.getValue("product_name")}" +
//                        "\nstatus: ${listResult.status}" +
//                        "\nstatus_verbose: ${listResult.statusVerbose}"
//                )
                MarsUiState.Success(
                    Product(
                        code = listResult.code,
                        product = listResult.product,
                        status = listResult.status,
                        statusVerbose = listResult.statusVerbose
                    )
                )
//                MarsUiState.Success(listResult)
            } catch (e: Exception) {
                e.printStackTrace()

                MarsUiState.Error
            }
        }
    }
}
