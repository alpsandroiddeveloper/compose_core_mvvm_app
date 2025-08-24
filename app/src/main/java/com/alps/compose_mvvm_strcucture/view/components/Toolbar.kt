package com.alps.compose_mvvm_strcucture.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.alps.compose_mvvm_strcucture.navigation.NavigationItem
import com.alps.compose_mvvm_strcucture.view.ui.theme.ColorLightGrey

@Composable
fun MainToolbar(title: String, navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(ColorLightGrey),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier
                .weight(1f)
                .padding(18.dp),
            text = title,
            textAlign = TextAlign.Start,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        IconButton(
            onClick = {
                navController.navigate(NavigationItem.Favourite.route)
            }
        ) {
            Icon(imageVector = Icons.Default.FavoriteBorder, contentDescription = "")
        }
    }

}