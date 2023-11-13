package com.marcossan.scanner.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.marcossan.scanner.R
import com.marcossan.scanner.data.model.Product
import com.marcossan.scanner.ui.navigation.Screens
import com.marcossan.scanner.ui.viewmodel.ProductViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navController: NavController,
    productViewModel: ProductViewModel,
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = { ScreenTopAppBar(scrollBehavior = scrollBehavior) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(route = Screens.AddProductScreen.route)
                },
                shape = CircleShape
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Floating action button.",
                    tint = Color.White
                )
            }
        }
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            ProductList(
                navController = navController,
                productViewModel = productViewModel
            )
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenTopAppBar(scrollBehavior: TopAppBarScrollBehavior, modifier: Modifier = Modifier) {
    CenterAlignedTopAppBar(
        scrollBehavior = scrollBehavior,
        title = {
            Text(
                text = stringResource(R.string.app_name),
                style = MaterialTheme.typography.headlineSmall,
            )
        },
        modifier = modifier
    )
}

@Composable
fun ProductList(
    navController: NavController,
    productViewModel: ProductViewModel
) {
    LazyColumn {
        items(productViewModel.listaProductos) { product ->
            ProductListItem(navController = navController, product = product, onOpenProductItem = {
                navController.navigate(route = "scanner/${product.code}")
            })
        }
    }
}


@Composable
fun ProductListItem(
    navController: NavController,
    product: Product,
    onOpenProductItem: () -> Unit,
) {
    // You can customize the appearance of each item here.

    Card(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 2.dp)
            .clickable { onOpenProductItem },
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp,
            pressedElevation = 8.dp,
            focusedElevation = 8.dp,
            hoveredElevation = 8.dp,
            draggedElevation = 8.dp,
            disabledElevation = 8.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onOpenProductItem() }
        ) {
            Box(modifier = Modifier.align(CenterVertically)) {
                AsyncImage(
                    model = product.imageUrl,
                    contentDescription = "",
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .size(80.dp)
                    ,
                    alignment = Alignment.Center
                )
            }
            Column(modifier = Modifier.padding(8.dp)) {
                // Nombre
                Text(
                    text = product.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                )
                // Marca
                Text(
                    text = product.code,
                )
                // Fecha caducidad
                Text(
                    text = "Fecha caducidad: " + product.expirationDate,
                )
                // Fecha añadido
                Text(
                    text = "Añadido: " + product.dateAdded,
                )
            }

        }
    }

//    Row(
//        modifier = Modifier
//            .fillMaxWidth()
//            .clickable { onOpenProductItem() }
//    ) {
//        AsyncImage(
//            model = product.imageUrl,
//            contentDescription = "",
//            modifier = Modifier
//                .padding(top = 15.dp)
//                .size(80.dp)
//        )
//        Column(modifier = Modifier.padding(16.dp)) {
//            // Nombre
//            Text(
//                text = product.name,
//                fontWeight = FontWeight.Bold,
//                fontSize = 16.sp,
//            )
//            // Marca
//            Text(
//                text = product.code,
//            )
//            // Fecha caducidad
//            Text(
//                text = product.expirationDate,
//            )
//            // Fecha añadido
//            Text(
//                text = product.dateAdded,
//            )
//        }
//
//    }
//    Divider()

}