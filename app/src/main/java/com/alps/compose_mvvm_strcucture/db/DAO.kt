package com.alps.compose_mvvm_strcucture.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

// Data Access Object (DAO) for interacting with the product table in the database
@Dao
interface ProductDAO {

    // Insert a new product into the database. This is a suspend function, meaning it should be called from a coroutine or another suspend function.
    @Insert
    suspend fun insertData(dbProduct: DbProduct)

    // Delete a product from the database. Also a suspend function.
    @Delete
    suspend fun deleteData(dbProduct: DbProduct)

    // Query to get all products from the database. It returns a Flow of a list of DbProduct objects.
    // Flow is used for asynchronous data streams, and the UI can observe changes to the data.
    @Query("SELECT * from product")
    fun getProductList(): Flow<List<DbProduct>>
}
