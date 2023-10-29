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
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.marcossan.scanner.network.Product
import com.google.gson.stream.JsonReader
import com.marcossan.scanner.R
import kotlinx.serialization.Serializable
import java.io.StringReader


@Serializable
data class User(val name: String, val yearOfBirth: Int)


@Composable
fun ProductScreen(
    navController: NavController,
    marsUiState: MarsUiState,
    modifier: Modifier = Modifier
) {
    when (marsUiState) {
        is MarsUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
        is MarsUiState.Success -> ResultScreen(
            marsUiState.photos,
            modifier = modifier.fillMaxWidth()
        )

        is MarsUiState.Error -> ErrorScreen(modifier = modifier.fillMaxSize())
    }
}

/**
 * ResultScreen displaying number of photos retrieved.
 */
@Composable
fun ResultScreen(photos: Product, modifier: Modifier = Modifier) {

    val barcode = photos.code
//    var nombreProducto = photos.product.getValue("product_name_es")
//    val imageURL = photos.product.getValue("image_front_url").toString()
    var nombreProducto = ""
    var imageURL: String = ""

    val stringReader = StringReader(photos.product.toString())
    val jsonReader = JsonReader(stringReader)

    jsonReader.beginObject() // Start reading the JSON object
    while (jsonReader.hasNext()) {
        val name = jsonReader.nextName()
        when (name) {
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

            "city" -> {
                val value = jsonReader.nextString()
                println("City: $value")
            }

            else -> jsonReader.skipValue() // Handle unexpected fields
        }
    }
    jsonReader.endObject() // End of the JSON object

    jsonReader.close() // Close the JsonReader when done


    LazyColumn(
        modifier = modifier
    ) {
        items(listOf(photos)) {
            Column {
                Text(text = "Código de barras: $barcode")
                Text(text = "Nombre del producto: $nombreProducto")
                Box(modifier = Modifier.fillMaxSize()) {
                    AsyncImage(
                        model = imageURL,
                        contentDescription = "",
                        modifier = Modifier
                            .padding(top = 15.dp)
                            .size(500.dp)
                    )
                }
            }

        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun ResultScreenPreview() {
//    MarsPhotosTheme {
//        ResultScreen(stringResource(R.string.placeholder_result))
//
//    }
//}

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