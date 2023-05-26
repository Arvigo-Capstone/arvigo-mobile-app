package id.arvigo.arvigobasecore.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Wishlist : Screen("wishlist")
    object Profile : Screen("profile")
    object Login : Screen("login")
    object Register : Screen("register")
    object DetailReward : Screen("home/{rewardId}") {
        fun createRoute(rewardId: Long) = "home/$rewardId"
    }
    object StoreWishlist : Screen("store_wishlist")
    object ProductWishlist : Screen("product_wishlist")
}
