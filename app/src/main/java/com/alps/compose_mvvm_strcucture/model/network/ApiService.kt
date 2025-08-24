package com.alps.compose_mvvm_strcucture.model.network

import com.alps.compose_mvvm_strcucture.model.dataModels.Product
import com.alps.compose_mvvm_strcucture.model.dataModels.ProductResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

// ApiService defines the network endpoints and how to interact with the API
interface ApiService {

    // GET request to fetch a list of products from the server
    // The response is wrapped in a Response object which will contain the ProductResponse
    @GET("products")
    suspend fun getProduct(): Response<ProductResponse>

    // GET request to fetch the details of a specific product by its ID
    // The product ID is passed as a path parameter in the URL
    @GET("products/{id}")
    suspend fun getProductDetails(
        @Path("id") id: String  // The {id} in the URL will be replaced with the actual product ID
    ): Response<Product>
}
