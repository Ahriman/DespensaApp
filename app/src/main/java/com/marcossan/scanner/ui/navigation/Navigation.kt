package com.marcossan.scanner.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.marcossan.scanner.ui.screens.MainScreen
import com.marcossan.scanner.ui.viewmodel.ProductViewModel
import com.marcossan.scanner.ui.screens.ScannerApp
import com.marcossan.scanner.ui.screens.AddProductScreen

@Composable
fun Navigation() {

    val navController = rememberNavController()
    val productViewModel: ProductViewModel = viewModel()

    NavHost(navController, startDestination = Screens.MainScreen.route) {
//        composable(route = Screens.MenuScreen.route) { MenuScreen(navController) }
//        composable(
//            route = Screens.MenuScreen.route,
//            arguments = listOf(navArgument("barcode") { type = NavType.StringType })
//
//        ) {
//            val barcode = it.arguments?.getString("barcode")
//            requireNotNull(barcode) { "No puede ser nulo porque la pantalla de producto necesita un código de barras" }
//            MenuScreen(navController, barcode = barcode)
//        }
        composable(route = Screens.MainScreen.route) {
            MainScreen(
                navController = navController,
                productViewModel = productViewModel
            )
        }
        composable(route = Screens.AddProductScreen.route,
            arguments = listOf(navArgument("barcode") { type = NavType.StringType })
        ) {
            val barcode = it.arguments?.getString("barcode")
            requireNotNull(barcode) { "No puede ser nulo porque la pantalla de producto necesita un código de barras" }

            AddProductScreen(
                navController = navController,
                productViewModel = productViewModel,
                barcode = barcode
            )
        }
//        composable(route = Screens.ScannerApp.route) {
//            ScannerApp(navController, productViewModel = productViewModel)
//        }

        composable(
            route = Screens.ScannerApp.route,
            arguments = listOf(navArgument("barcode") { type = NavType.StringType })
        ) {
            val barcode = it.arguments?.getString("barcode")
            requireNotNull(barcode) { "No puede ser nulo porque la pantalla de producto necesita un código de barras" }
            ScannerApp(navController, productViewModel = productViewModel, barcode = barcode)
        }

    }
}



