package com.alps.compose_mvvm_strcucture.navigation

import ProductScreen
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.alps.compose_mvvm_strcucture.view.pages.FavouriteScreen
import com.alps.compose_mvvm_strcucture.view.pages.ProductDetailScreen

// The main navigation host for the app, which handles all screen navigation
@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,  // Modifier for styling (not used in this example, but can be passed)
    navController: NavHostController = rememberNavController(),  // Creates or remembers the NavController for navigation
    startDestination: String = NavigationItem.Product.route,  // Defines the starting screen route (Product screen)
) {
    // NavHost is the container that holds the navigation logic and controls screen transitions
    NavHost(
        navController = navController,  // NavController is responsible for navigating between screens
        startDestination = startDestination  // The first screen to be shown when the app starts
    ) {
        // Composable for the Product screen (home screen of the app)
        composable(NavigationItem.Product.route) {
            ProductScreen(navController)  // Displays the product screen and passes the navController
        }

        // Composable for the Product Details screen, with dynamic argument for product ID
        composable(NavigationItem.ProductDetails.route + "/{prodId}", arguments = listOf(
            navArgument("prodId") {  // Define the product ID as an argument
                type = NavType.IntType  // The argument type is Integer
            }
        )) {
            val prodId = it.arguments?.getInt("prodId")  // Get the product ID from the arguments
            Log.d("id", NavigationItem.ProductDetails.route + prodId)  // Log the product ID (for debugging)
            ProductDetailScreen(prodId)  // Pass the product ID to the ProductDetailScreen
        }

        // Composable for the Favourite screen
        composable(NavigationItem.Favourite.route) {
            FavouriteScreen()  // Displays the favourite products screen
        }
    }
}

// The commented-out GlobalNavigator code seems to be for global navigation handling, but it's not being used here.
// It would allow navigation control from anywhere in the app, but it’s currently not active.
