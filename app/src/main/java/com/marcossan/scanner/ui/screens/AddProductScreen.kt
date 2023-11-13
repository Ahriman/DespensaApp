package com.marcossan.scanner.ui.screens

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.marcossan.scanner.BarcodeScanner
import com.marcossan.scanner.R
import com.marcossan.scanner.data.model.Product
import com.marcossan.scanner.ui.navigation.Screens
import com.marcossan.scanner.ui.viewmodel.ProductViewModel
import kotlinx.coroutines.launch
import java.util.Calendar

@Composable
fun AddProductScreen(
    navController: NavController,
    productViewModel: ProductViewModel,
    barcode: String
) {

    lateinit var barcodeScanner: BarcodeScanner

    val context = LocalContext.current
    barcodeScanner = BarcodeScanner(context, navController)

//    val barcodeResults = barcodeScanner.barCodeResults.collectAsStateWithLifecycle()

    ScanBarcode(
        navController = navController,
//        onScanBarcode = barcodeScanner::startScan,
//        onValueChange = { productViewModel.onBarcodeChange(it) },
        onScanBarcode = { barcodeScanner.startScan(productViewModel) },
        productViewModel = productViewModel,
        modifier = Modifier
    )

}


@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScanBarcode(
    navController: NavController,
    onScanBarcode: suspend () -> Unit,
    productViewModel: ProductViewModel,
    modifier: Modifier = Modifier
) {
//    val localUriHandler = LocalUriHandler.current
    val scope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()


    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = {
//                    Text(text = vm.loggedUser?.name ?: stringResource(id = R.string.login))
                    Text(text = "Añadir producto")
                },
                modifier = Modifier.fillMaxWidth(),
                actions = {
//                    vm.loggedUser?.let {
//                        Button(onClick = {
//                            vm.logOut()
//                        }) {
//                            Text(text = stringResource(id = R.string.logout))
//                        }
//                    }
//                    Button(onClick = {
////                onAddProduct(productName)
//
////            focusManager.clearFocus() // Esconde el teclado
//                    }) {
////                        Text(text = stringResource(id = R.string.add_button))
//                        Icon(
//                            imageVector = Icons.Outlined.Check,
//                            contentDescription = stringResource(id = R.string.add_button)
//                        )
//                    }
                    Icon(
                        imageVector = Icons.Filled.Check,
                        contentDescription = stringResource(id = R.string.add_button),
                        modifier = Modifier
                            .padding(8.dp)
                            .clickable {
                                // TODO: Añadir producto a la lista
                                scope.launch {
//                                    productViewModel.getProductFromApi(productViewModel.barcode)
                                    productViewModel.listaProductos.add(
                                        Product(
                                            code = productViewModel.barcode,
                                            name = productViewModel.productName,
                                            imageUrl = productViewModel.productUrl,
                                            expirationDate = productViewModel.productExpireDate,
                                            dateAdded = productViewModel.productDateAdded,
                                            quantity = productViewModel.productQuantity
                                        )
                                    )
                                    navController.navigate(route = Screens.MainScreen.route)
                                }

                            }
                    )
                }
            )
        }
    ) {

        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
//                verticalArrangement = Arrangement.Center
            ) {
                item {
                    val focusManager = LocalFocusManager.current
                    // TODO: Revisar

                    Spacer(modifier = Modifier.padding(top = 4.dp))
                    Row(
                        modifier.padding(vertical = 8.dp, horizontal = 10.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        //verticalAlignment = Alignment.CenterVertically,
                    ) {

                        OutlinedTextField(
                            value = productViewModel.barcode,
                            onValueChange = { barcode -> productViewModel.onBarcodeChange(barcode) },
                            modifier = modifier.weight(0.8f),
                            label = { Text(text = stringResource(R.string.product_barcode)) },
                            trailingIcon = {
                                Icon(painter = painterResource(id = R.drawable.barcode_scanner),
                                    contentDescription = stringResource(R.string.open_barcode_scanner_description),
                                    modifier = Modifier.clickable {
                                        scope.launch {
                                            onScanBarcode()
                                        }
                                    })
                            },
                            keyboardOptions = KeyboardOptions(
                                capitalization = KeyboardCapitalization.Sentences
                            ),
                            singleLine = true,
                        )

                    }

                    Row(
                        modifier.padding(vertical = 8.dp, horizontal = 10.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        OutlinedTextField(
                            value = productViewModel.productName,
                            onValueChange = { productName ->
                                productViewModel.onProductNameChange(
                                    productName
                                )
                            },
                            modifier = Modifier.weight(0.8f),
                            label = { Text(text = stringResource(R.string.product_name)) },
                            keyboardOptions = KeyboardOptions(
                                capitalization = KeyboardCapitalization.Sentences
                            ),
                            singleLine = true,
                        )
                    }

                    Spacer(modifier = modifier.padding(5.dp))

                    MyDatePicker(productViewModel = productViewModel, modifier = modifier)

                    Spacer(modifier = modifier.padding(5.dp))

                    Row(
                        modifier.padding(vertical = 8.dp, horizontal = 10.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        OutlinedTextField(
//                        value = productViewModel.productQuantity.replaceFirstChar { it.uppercase() },
                            value = productViewModel.productQuantity,
                            onValueChange = { productQuantity ->
                                productViewModel.onProductQuantityChange(
                                    productQuantity
                                )
                            },
                            modifier = Modifier.weight(0.8f),
                            label = { Text(text = stringResource(R.string.product_quantity)) },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number
                            ),
                            singleLine = true,
                        )
                    }

                    Spacer(modifier = modifier.padding(5.dp))

                    // La primera vez que entramos a la pantalla, no intenta cargar los datos
                    // La segunda vez que se entra, es después de escanear el código y ya carga correctamente los datos.
                    if (productViewModel.isBarcodeScanned) {
                        scope.launch {
                            productViewModel.getProductFromApi(productViewModel.barcode)
                            productViewModel.setIsBarcodeScanned(false)
                        }
                    }

                }
            }
        }
    }


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyDatePicker(productViewModel: ProductViewModel, modifier: Modifier) {

    val mCalendar = Calendar.getInstance()
    val year = mCalendar.get(Calendar.YEAR)
    val month = mCalendar.get(Calendar.MONTH)
    val day = mCalendar.get(Calendar.DAY_OF_MONTH)

    val mDatePickerDialog = DatePickerDialog(
        LocalContext.current,
        { _, year: Int, month: Int, day: Int ->
            productViewModel.onProductExpireDateChange("$day/${month - 1}/$year")
        }, year, month, day
    )

    Row(
        modifier
            .padding(vertical = 8.dp, horizontal = 10.dp)
            .clickable {
                mDatePickerDialog.show()
            },
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        OutlinedTextField(
            value = productViewModel.productExpireDate,
            onValueChange = { productViewModel.onProductExpireDateChange(it) },
            modifier = modifier.weight(0.8f),
            readOnly = true,
            label = { Text(text = stringResource(R.string.product_expire_data)) },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Filled.DateRange,
                    contentDescription = null,
                    modifier = Modifier
                        .size(24.dp)
//                        .padding(4.dp)
                        .clickable {
                            mDatePickerDialog.show()
                        }
                )
            },
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Sentences
            ),
            singleLine = true,
        )
//        Icon(
//            imageVector = Icons.Filled.DateRange,
//            contentDescription = null,
//            modifier = Modifier
//                .size(60.dp)
//                .padding(4.dp)
//                .clickable {
//                    mDatePickerDialog.show()
//                }
//        )
    }

}
