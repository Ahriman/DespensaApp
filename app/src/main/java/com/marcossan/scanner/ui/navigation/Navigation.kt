package com.marcossan.scanner.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.marcossan.scanner.MenuScreen
import com.marcossan.scanner.ui.screens.MarsPhotosApp
import com.marcossan.scanner.ui.screens.ProductScreen
import com.marcossan.scanner.ui.screens.ProductViewModel
import com.marcossan.scanner.ui.screens.ScannerScreen

@Composable
fun Navigation(
    //productViewModel: ProductViewModel
//    marsUiState: MarsUiState
) {
//    val productViewModel = ProductViewModel()
//    val marsUiState = productViewModel.marsUiState
    val navController = rememberNavController()
    val productViewModel: ProductViewModel = viewModel()
    NavHost(navController, startDestination = Screens.MenuScreen.route) {
        composable(route = Screens.MenuScreen.route) { MenuScreen(navController) }
        composable(route = Screens.ScannerScreen.route) { ScannerScreen(navController = navController, productViewModel = productViewModel) }
        composable(route = Screens.MarsPhotosApp.route) { MarsPhotosApp(navController, productViewModel = productViewModel) }
    }
}



