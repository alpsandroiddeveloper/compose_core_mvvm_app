package com.alps.compose_mvvm_strcucture.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.alps.compose_mvvm_strcucture.R
import com.alps.compose_mvvm_strcucture.model.dataModels.Product
import com.alps.compose_mvvm_strcucture.navigation.NavigationItem
import com.alps.compose_mvvm_strcucture.view.ui.theme.ColorLightGrey

@Composable
fun ProductView(products: List<Product>, navController: NavController) {
    // A vertical scrolling grid layout with 2 fixed columns
    LazyVerticalGrid(
        columns = GridCells.Fixed(2), // Fixed 2 columns in the grid
        modifier = Modifier
            .fillMaxSize()           // Fill the available size of the screen
            .padding(15.dp),          // Outer padding around the grid
        verticalArrangement = Arrangement.spacedBy(12.dp),   // Vertical spacing between rows
        horizontalArrangement = Arrangement.spacedBy(12.dp), // Horizontal spacing between columns
    ) {
        // Iterate over the list of products and create a card for each one
        items(products) { product ->
            ProductRowItemView(product, navController) // Composable to show a single product
        }
    }
}


@Composable
fun ProductRowItemView(product: Product, navController: NavController) {
    val context = LocalContext.current
    val gradient = Brush.verticalGradient(
        listOf(
            Color.Transparent,
            ColorLightGrey
        ),
    )
    Box(
        modifier = Modifier
            .fillMaxSize(1f)
            .aspectRatio(1f)
            .clip(RoundedCornerShape(10))
            .clickable {
                navController.navigate(NavigationItem.ProductDetails.route + "/${product.id}")
            },
        contentAlignment = Alignment.BottomCenter,
    ) {
        AsyncImage(
            model = product.thumbnail,
            placeholder = painterResource(id = R.drawable.dummy_product),
            contentDescription = "product_image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(10)),
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .size(70.dp)
                .background(gradient)
                .clip(RoundedCornerShape(10)),
            contentAlignment = Alignment.Center
        ) {
            Text(text = product.title, textAlign = TextAlign.Center, fontSize = 15.sp)
        }
    }
}
