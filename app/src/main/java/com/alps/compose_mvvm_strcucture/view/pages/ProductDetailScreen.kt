package com.alps.compose_mvvm_strcucture.view.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.alps.compose_mvvm_strcucture.utils.Resource
import com.alps.compose_mvvm_strcucture.view.components.ProductDetailsView
import com.alps.compose_mvvm_strcucture.viewmodel.AppViewModel

@Composable
fun ProductDetailScreen(prodId: Int?, appViewModel: AppViewModel = hiltViewModel()) {

    // Collects the product details from the ViewModel as StateFlow
    val prodDetails by appViewModel.productDetails.collectAsState()

    // LaunchedEffect is triggered when the screen is first composed or the key (Unit) changes
    LaunchedEffect(Unit) {
        appViewModel.getProductDetails(prodId.toString())  // Fetch product details for the given prodId from the ViewModel
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {

        when (val resource = prodDetails) {
            is Resource.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(50.dp)
                        .padding(5.dp)
                        .align(Alignment.CenterHorizontally),
                )
            }

            is Resource.Success -> {
                ProductDetailsView(resource.data, appViewModel)
            }

            is Resource.Error -> {
                Text(text = resource.message)
            }
        }

    }

}
