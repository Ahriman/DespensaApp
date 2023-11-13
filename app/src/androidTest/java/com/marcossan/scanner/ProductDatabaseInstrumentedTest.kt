package com.marcossan.scanner

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.marcossan.scanner.data.database.ProductDatabase
import com.marcossan.scanner.data.database.dao.ProductDao
import com.marcossan.scanner.data.database.entities.ProductEntity
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class SimpleEntityReadWriteTest {
    private lateinit var productDao: ProductDao
    private lateinit var db: ProductDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, ProductDatabase::class.java).build()
        productDao = db.getProductDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun writeProductAndReadAllProducts() {

        val code = "123456789"

        // Creamos una instancia de ProductEntity
        val productEntity = ProductEntity(
            id = 1,
            code = code,
            name = "123456789",
            imageUrl = "",
            expirationDate = "",
            quantity = "",
            dateAdded = ""
        )

        // Insertamos el producto en la base de datos de productos
        productDao.insert(productEntity = productEntity)

        // Leemos la base de datos de productos
        val products = productDao.getAll()

        // Borramos el producto que insertamos en la base de datos de productos
        productDao.delete(productEntity = productEntity)

        // Mostramos la lista de productos de la consulta por consola
        println(products)

        // Probamos si el resultado es el esperado
        assertEquals(products[0].code, code)

    }
}
