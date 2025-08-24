package com.alps.compose_mvvm_strcucture.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alps.compose_mvvm_strcucture.db.DbProduct
import com.alps.compose_mvvm_strcucture.model.dataModels.Product
import com.alps.compose_mvvm_strcucture.model.repo.AppRepository
import com.alps.compose_mvvm_strcucture.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    private val appRepository: AppRepository,  // Injecting the repository to fetch data from API and DB
) : ViewModel() {

    // StateFlow to represent the loading, success, or error state of the product list
    private val _product = MutableStateFlow<Resource<List<Product>>>(Resource.Loading())
    val product: StateFlow<Resource<List<Product>>> = _product  // Exposed as a read-only StateFlow for the UI to observe

    // StateFlow for product details (single product)
    private val _productDetails = MutableStateFlow<Resource<Product>>(Resource.Loading())
    val productDetails: StateFlow<Resource<Product>> = _productDetails  // Exposed as a read-only StateFlow for the UI to observe

    // StateFlow to represent the list of products stored in the local database
    private val _dbProductList = MutableStateFlow<List<DbProduct>>(emptyList())
    val dbProductList: StateFlow<List<DbProduct>> = _dbProductList  // Exposed as a read-only StateFlow for the UI to observe

    // LivaData (commented out), could be used if you prefer LiveData over StateFlow
    //val allData: LiveData<List<DbProduct>> = appRepository.getProductListFromDB()

    // Initialization block: Fetch products when the ViewModel is created
    init {
        fetchProduct()
    }

    // Function to fetch the list of products from the repository
    fun fetchProduct() {
        viewModelScope.launch {  // Launching a coroutine in the ViewModel's scope
            _product.value = Resource.Loading() // Emit a loading state before fetching data

            try {
                val response = appRepository.getProduct()  // Call repository to fetch products from API
                when (response) {
                    is Resource.Success -> _product.value = response  // On success, update the StateFlow with the product data
                    is Resource.Error -> _product.value = Resource.Error(response.message)  // On error, update with error message
                    else -> Unit  // If no result, do nothing
                }
            } catch (e: Exception) {
                _product.value = Resource.Error(e.message ?: "Unknown error")  // Handle exceptions and emit error state
            }
        }
    }

    // Function to get product details by product ID
    fun getProductDetails(prodId: String) {
        viewModelScope.launch {  // Launching a coroutine in the ViewModel's scope
            try {
                val result = appRepository.getProductDetails(prodId)  // Fetch product details from the repository
                when (result) {
                    is Resource.Success -> _productDetails.value = result  // On success, update the StateFlow with product details
                    is Resource.Error -> _product.value = Resource.Error(result.message)  // On error, emit error state for product list
                    else -> Unit  // If no result, do nothing
                }
            } catch (e: Exception) {
                // Handle exception, but no specific action is taken here (can be logged if needed)
            }
        }
    }

    // Function to fetch the list of products from the local database (via Flow)
    fun fetchProductListFromDB() {
        viewModelScope.launch {  // Launching a coroutine in the ViewModel's scope
            appRepository.getProductListFromDB()  // Collect the products from the DB
                .collect { list ->  // Collect the Flow of DbProducts
                    _dbProductList.value = list  // Update the StateFlow with the latest list from DB
                }
        }
    }

    // Function to insert a product into the local database
    fun insertDataToDb(product: DbProduct) {
        viewModelScope.launch(IO) {  // Launching a coroutine in the IO dispatcher for DB operations
            appRepository.insertData(product)  // Insert the product into the database
        }
    }

    // Function to delete a product from the local database
    fun deleteData(product: DbProduct) {
        viewModelScope.launch(IO) {  // Launching a coroutine in the IO dispatcher for DB operations
            appRepository.deleteData(product)  // Delete the product from the database
        }
    }
}

