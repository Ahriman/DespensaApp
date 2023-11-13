package com.marcossan.scanner.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.marcossan.scanner.data.database.utils.Constants

/**
 *
 */
@Entity(tableName = Constants.products_table_name)
data class ProductEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,
    @ColumnInfo(name = "code")
    val code: String,
    @ColumnInfo(name = "name")
    var name: String,
    @ColumnInfo(name = "image_url")
    val imageUrl: String,
    @ColumnInfo(name = "expiration_date")
    val expirationDate: String,
    @ColumnInfo(name = "date_added")
    val dateAdded: String,
    @ColumnInfo(name = "quantity")
    val quantity: String
) {
}