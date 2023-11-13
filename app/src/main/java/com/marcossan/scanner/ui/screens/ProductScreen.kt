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

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.marcossan.scanner.R
import com.marcossan.scanner.data.model.Product
import com.marcossan.scanner.ui.viewmodel.ProductViewModel
import com.marcossan.scanner.ui.viewmodel.ScannerUiState
import kotlinx.serialization.Serializable


@Serializable
data class User(val name: String, val yearOfBirth: Int)


@Composable
fun ProductScreen(
    navController: NavController,
    productViewModel: ProductViewModel,
    scannerUiState: ScannerUiState,
    modifier: Modifier = Modifier
//    onOpenProductItem: () -> Unit,
) {
    when (scannerUiState) {
        is ScannerUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
        is ScannerUiState.Success -> ResultScreen(
            productViewModel,
            scannerUiState.product,
            modifier = modifier.fillMaxWidth()
        )
        is ScannerUiState.Error -> ErrorScreen(modifier = modifier.fillMaxSize())
    }
}

/**
 * ResultScreen displaying number of photos retrieved.
 */
@Composable
fun ResultScreen(productViewModel: ProductViewModel, product: Product, modifier: Modifier = Modifier) {

    LazyColumn(
        modifier = modifier.padding(16.dp)
    ) {
        item {
            Text(text = "Código de barras: ${product.code}")
            Text(text = "Nombre: ${product.name}")
            Text(text = "Fecha de caducidad: ${product.expirationDate}")
            Text(text = "Cantidad: ${productViewModel.productQuantity}")
            // TODO: Arreglar fecha, porque se actualiza al hacer scroll
            Text(text = "Añadido el día: ${product.dateAdded}")

            Box(modifier = Modifier.fillMaxSize()) {
                AsyncImage(
                    model = product.imageUrl,
                    contentDescription = "",
                    modifier = Modifier
                        .padding(top = 15.dp)
                        .size(300.dp)
                )
            }
        }
    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(R.drawable.loading_img),
        contentDescription = stringResource(R.string.loading)
    )
}

@Composable
fun ErrorScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_connection_error), contentDescription = ""
        )
        Text(text = stringResource(R.string.loading_failed), modifier = Modifier.padding(16.dp))
    }
}

fun String.eliminarDoblesComillas() {
    this.removeSurrounding("\"")
}