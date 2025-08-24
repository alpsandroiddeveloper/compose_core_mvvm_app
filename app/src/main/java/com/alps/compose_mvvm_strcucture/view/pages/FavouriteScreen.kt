package com.alps.compose_mvvm_strcucture.view.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.alps.compose_mvvm_strcucture.view.components.FavoriteProductList
import com.alps.compose_mvvm_strcucture.view.ui.theme.ColorLightGrey
import com.alps.compose_mvvm_strcucture.viewmodel.AppViewModel


@Composable
fun FavouriteScreen(modifier: Modifier = Modifier, appViewModel: AppViewModel = hiltViewModel()) {

// Live data (commented out)
    // val dbListState by appViewModel.dbProductList.collectAsState(emptyList())  // If using LiveData, collect it as state

    // StateFlow (preferred in this case, since we are observing changes in the database)
    val favoriteProductList by appViewModel.dbProductList.collectAsState()  // Collect the list of products from the database as state

    // Launch an effect when the screen is composed for the first time
    LaunchedEffect(Unit) {
        appViewModel.fetchProductListFromDB()  // Fetch the list of products from the database when the screen loads
    }

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Text(
                text = "Favourite",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(ColorLightGrey)
                    .padding(18.dp)
            )

            FavoriteProductList(
                modifier, favoriteProductList,appViewModel
            )

        }

    }

}
