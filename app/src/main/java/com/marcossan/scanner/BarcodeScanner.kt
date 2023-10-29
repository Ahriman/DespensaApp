package com.marcossan.scanner

import android.content.Context
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning
import kotlinx.coroutines.flow.MutableStateFlow

class BarcodeScanner(
    appContext: Context
) {
    /**
     * From the docs: If you know which barcode formats you expect to read, you can improve the
     * speed of the barcode detector by configuring it to only detect those formats.
     */
    private val options = GmsBarcodeScannerOptions.Builder()
        .setBarcodeFormats(Barcode.FORMAT_ALL_FORMATS)
        .build()

    private val scanner = GmsBarcodeScanning.getClient(appContext, options)
    val barCodeResults = MutableStateFlow<String?>(null)

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
}