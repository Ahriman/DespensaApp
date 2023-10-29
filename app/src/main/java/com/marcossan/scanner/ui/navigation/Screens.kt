package com.marcossan.scanner.ui.navigation

sealed class Screens(val route: String){
//    object MainScreen: Screens("initial_screen")
    object MenuScreen: Screens("menu_screen")

    object ScannerScreen: Screens("scanner_screen")
    object MarsPhotosApp: Screens("mars_photos_app_screen")

//    object ProductScreen: Screens("product_screen")

}
