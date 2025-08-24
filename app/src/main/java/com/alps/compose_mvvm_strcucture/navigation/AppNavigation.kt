package com.alps.compose_mvvm_strcucture.navigation

enum class Screen {
    PRODUCT,
    PRODUCT_DETAILS,
    FAVOURITE
}
sealed class NavigationItem(val route: String) {
    object Product : NavigationItem(Screen.PRODUCT.name)
    object ProductDetails : NavigationItem(Screen.PRODUCT_DETAILS.name)
    object Favourite : NavigationItem(Screen.FAVOURITE.name)

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg -> append("/$arg") }
        }
    }

}