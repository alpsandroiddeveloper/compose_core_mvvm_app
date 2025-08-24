package com.alps.compose_mvvm_strcucture.view.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.alps.compose_mvvm_strcucture.R
import com.alps.compose_mvvm_strcucture.db.DbProduct
import com.alps.compose_mvvm_strcucture.view.ui.theme.ColorRed
import com.alps.compose_mvvm_strcucture.viewmodel.AppViewModel

@Composable
fun FavoriteProductList(
    modifier: Modifier = Modifier,
    favoriteProductList: List<DbProduct>,
    appViewModel: AppViewModel
) {
    if (favoriteProductList.isNotEmpty()) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = modifier
                .fillMaxSize(),
            contentPadding = PaddingValues(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)

        ) {
            if (favoriteProductList.isNotEmpty()) {
                items(favoriteProductList) { item ->
                    FavItemRow(modifier, item, appViewModel)
                }
            }

        }
    } else {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Data not found...")
        }
    }

}

@Composable
fun FavItemRow(modifier: Modifier, data: DbProduct, appViewModel: AppViewModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .clip(RoundedCornerShape(10.dp))
    ) {
        Box(
            modifier = modifier.fillMaxSize()
        ) {
            Icon(
                imageVector = Icons.Default.Favorite,
                contentDescription = "",
                tint = ColorRed,
                modifier = modifier
                    .align(alignment = Alignment.TopStart)
                    .padding(5.dp)
            )
            IconButton(
                onClick = {
                    appViewModel.deleteData(data)
                },
                modifier = modifier
                    .align(alignment = Alignment.TopEnd)
            ) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "")
            }


            Column(
                modifier.padding(5.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AsyncImage(
                    model = data.thumbnailUtils,
                    placeholder = painterResource(id = R.drawable.dummy_product),
                    contentDescription = "product_image",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .size(125.dp)
                        .clip(RoundedCornerShape(10))
                )


                Text(
                    text = data.prodTitle,
                    modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    maxLines = 2
                )
            }

        }

    }
}
