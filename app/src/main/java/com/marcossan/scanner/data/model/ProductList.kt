package com.marcossan.scanner.data.model

import kotlinx.serialization.Serializable

@Serializable
class ProductList {
    val list = mutableListOf(
        Product(
            code = "8424818268292",
            name = "Soja",
            "https://images.openfoodfacts.org/images/products/842/481/826/8292/front_es.9.400.jpg",
        ),
        Product(
            code = "8480010090277",
            name = "Canela",
            "https://images.openfoodfacts.org/images/products/848/001/009/0277/front_es.6.400.jpg",
        ),
        Product(
            code = "8480000073297",
            name = "Soja texturizada",
            "https://images.openfoodfacts.org/images/products/848/000/007/3297/front_es.99.400.jpg",
        ),
//        Product(
//            code = "8431876291117",
//            name = "Rulo tofu y algas",
//            "https://images.openfoodfacts.org/images/products/843/187/629/1117/front_es.19.400.jpg",
//        ),
//        Product(
//            code = "20659318",
//            name = "Bebida de Avena",
//            "https://images.openfoodfacts.org/images/products/20659318/front_es.77.400.jpg",
//        ),
//        Product(
//            code = "8422767123167",
//            name = "Aceite De Oliva Virgen Extra\"",
//            "https://images.openfoodfacts.org/images/products/842/276/712/3167/front_es.29.400.jpg",
//        ),
    )
}