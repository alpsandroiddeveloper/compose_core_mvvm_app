package com.alps.compose_mvvm_strcucture.view.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.alps.compose_mvvm_strcucture.R
import com.alps.compose_mvvm_strcucture.db.DbProduct
import com.alps.compose_mvvm_strcucture.model.dataModels.Product
import com.alps.compose_mvvm_strcucture.utils.showToast
import com.alps.compose_mvvm_strcucture.utils.verticalSpace
import com.alps.compose_mvvm_strcucture.view.ui.theme.ColorBlack
import com.alps.compose_mvvm_strcucture.view.ui.theme.ColorGrey
import com.alps.compose_mvvm_strcucture.viewmodel.AppViewModel
import kotlinx.coroutines.launch
import mx.platacard.pagerindicator.PagerIndicatorOrientation
import mx.platacard.pagerindicator.PagerWormIndicator

@Composable
fun ProductDetailsView(prodData: Product, appViewModel: AppViewModel = hiltViewModel()) {
    val context = LocalContext.current

    val coroutineScope = rememberCoroutineScope()

    val pagerState = rememberPagerState(pageCount = {
        prodData.images.size
    })

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                text = prodData.title,
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            verticalSpace(10.dp)

            HorizontalPager(state = pagerState) { page ->
                // Our page content
                Card(
                    modifier = Modifier
                        .height(300.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .padding(vertical = 5.dp, horizontal = 30.dp),
                ) {

                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.BottomCenter
                    ) {
                        AsyncImage(
                            model = prodData.images[page],
                            placeholder = painterResource(id = R.drawable.dummy_product),
                            contentDescription = "product_image",
                            contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(RoundedCornerShape(10))
                        )

                        PagerWormIndicator(
                            modifier = Modifier
                                .padding(10.dp),
                            pagerState = pagerState,
                            activeDotColor = ColorBlack,
                            dotColor = ColorGrey,
                            dotCount = if (prodData.images.size == 1) 0 else prodData.images.size,
                            orientation = PagerIndicatorOrientation.Horizontal
                        )
                    }


                }
            }

            Text(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(20.dp),
                text = prodData.description,
                textAlign = TextAlign.Center,
                fontSize = 15.sp,
            )

            verticalSpace(100.dp)
            Button(
                onClick = {
                    val insertProduct = DbProduct(
                        id = prodData.id,
                        prodTitle = prodData.title,
                        description = prodData.description,
                        thumbnailUtils = prodData.thumbnail
                    )
                    coroutineScope.launch {
                        appViewModel.insertDataToDb(insertProduct)
                        showToast(context, "Added successfully")
                    }

                }
            ) {
                Text(
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(5.dp),
                    text = "Add to fav",
                    textAlign = TextAlign.Center,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}