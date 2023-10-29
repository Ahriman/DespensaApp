package com.marcossan.scanner.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.marcossan.scanner.BarcodeScanner
import com.marcossan.scanner.ui.navigation.Screens
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun ScannerScreen (
    navController: NavController,
    productViewModel: ProductViewModel
) {

//    lateinit var barcodeScanner: BarcodeScanner

//    val context = LocalContext.current
//    barcodeScanner = BarcodeScanner(context)

//    val barcodeResults = barcodeScanner.barCodeResults.collectAsStateWithLifecycle()

    ScanBarcode(
//        onScanBarcode = barcodeScanner::startScan,
        onScanBarcode = { productViewModel.startScan() },
        barcodeValue = productViewModel.barCodeResults.value,
        productViewModel = productViewModel
    )

    Column {
        Button(onClick = { navController.navigate(route = Screens.MenuScreen.route) }) {
            Text(text = "Volver atrás")
        }
    }

}


@Composable
fun ScanBarcode(
    onScanBarcode: suspend () -> Unit,
    barcodeValue: String?,
    productViewModel: ProductViewModel
) {
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Button(modifier = Modifier.fillMaxWidth(.85f),
            colors = ButtonDefaults.buttonColors(
                contentColor = Color.Black
            ), onClick = {
                scope.launch {
                    onScanBarcode()
                }
            }
        ) {
            Text(
                text = "Escanear alimento", //Scan Barcode
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.displaySmall,
//                color = Color.LightGray
//                style = TextStyle(fontWeight = FontWeight.Bold)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
//            text = barcodeValue ?: "0000000000", //0000000000
            text = productViewModel.barCodeResults.value ?: "0000000000", //0000000000
            style = MaterialTheme.typography.displayMedium
        )

    }
}