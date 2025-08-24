package com.alps.compose_mvvm_strcucture.model.repo

import android.util.Log
import com.alps.compose_mvvm_strcucture.db.DbProduct
import com.alps.compose_mvvm_strcucture.db.ProductDAO
import com.alps.compose_mvvm_strcucture.model.dataModels.Product
import com.alps.compose_mvvm_strcucture.model.network.ApiService
import com.alps.compose_mvvm_strcucture.utils.AppConstant
import com.alps.compose_mvvm_strcucture.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

// Repository class that acts as a data source for both remote (API) and local (database) data
class AppRepository @Inject constructor(
    private val apiService: ApiService,  // Injected API service for network requests
    private val dao: ProductDAO,        // Injected DAO for local database operations
) {

    // Fetches the list of products from the API
    suspend fun getProduct(): Resource<List<Product>> {
        return try {
            val response = apiService.getProduct()  // Make a network request to get products
            if (response.isSuccessful) {
                response.body()?.let { productResponse ->
                    // If the response is successful, return the list of products
                    Resource.Success(productResponse.products)
                } ?: Resource.Error("Empty response from server") // Handle empty response
            } else {
                Resource.Error("Error: ${response.code()} - ${response.message()}")  // Handle non-2xx responses
            }
        } catch (e: Exception) {
            Log.d(AppConstant.TAG, "Exception ::: ${e.toString()}")  // Log the exception
            Resource.Error("Exception: ${e.localizedMessage ?: "Unknown error"}")  // Return the error message
        }
    }

    // Fetches the details of a specific product by its ID from the API
    suspend fun getProductDetails(prodId: String): Resource<Product> {
        return try {
            val response =
                apiService.getProductDetails(prodId)  // Make network request for product details
            if (response.isSuccessful) {
                response.body()?.let {
                    Resource.Success(it)  // Return the product details if successful
                } ?: Resource.Error("Empty response from server")  // Handle empty response
            } else {
                Resource.Error("Error: ${response.code()} - ${response.message()}")  // Handle error responses
            }
        } catch (e: Exception) {
            Resource.Error("Exception: ${e.localizedMessage ?: "Unknown error"}")  // Return error if an exception occurs
        }
    }

    // Fetches the list of products from the local database (using StateFlow for real-time updates)
    fun getProductListFromDB(): Flow<List<DbProduct>> {
        return dao.getProductList()  // Returns a Flow that emits the product list from the database
    }

    // Inserts a new product into the local database
    suspend fun insertData(dbProduct: DbProduct) {
        return dao.insertData(dbProduct)  // Calls DAO to insert the product
    }

    // Deletes a product from the local database
    suspend fun deleteData(dbProduct: DbProduct) {
        return dao.deleteData(dbProduct)  // Calls DAO to delete the product
    }

    // Live data
//    fun getProductListFromDB(): LiveData<List<DbProduct>> {
//        return dao.getProductList()
//    }

}


