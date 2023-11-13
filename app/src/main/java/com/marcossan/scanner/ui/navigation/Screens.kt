package com.marcossan.scanner.ui.navigation

sealed class Screens(val route: String){
    data object MainScreen: Screens("main_screen")
    data object AddProductScreen: Screens("scanner_screen/{barcode}")
    data object ScannerApp: Screens("scanner/{barcode}")
    data object ProductScreen: Screens("product_screen")

}
