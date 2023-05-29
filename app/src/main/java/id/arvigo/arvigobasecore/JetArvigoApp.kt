package id.arvigo.arvigobasecore

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import id.arvigo.arvigobasecore.ui.feature.home.HomeScreen
import id.arvigo.arvigobasecore.ui.feature.personality.PersonalityMainTestScreen
import id.arvigo.arvigobasecore.ui.feature.personality.PersonalityResultScreen
import id.arvigo.arvigobasecore.ui.feature.personality.PersonalityScreen
import id.arvigo.arvigobasecore.ui.feature.profile.PricingScreen
import id.arvigo.arvigobasecore.ui.feature.profile.ProfileEditScreen
import id.arvigo.arvigobasecore.ui.feature.profile.ProfileScreen
import id.arvigo.arvigobasecore.ui.feature.wishlist.WishListScreen
import id.arvigo.arvigobasecore.ui.navigation.*
import id.arvigo.arvigobasecore.ui.navigation.nav_graph.authNavGraph

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JetArvigoApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            if (currentRoute != Screen.Personality.route && currentRoute != Screen.PersonalityMainTest.route) {
                BottomBar(navController)
            }
        },
        modifier = modifier
    ) {
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(it)
        ) {
            composable(Screen.Home.route) {
                HomeScreen(
                    navController = navController,
                )
            }
            composable(Screen.Wishlist.route) {
                WishListScreen()
            }
            composable(Screen.Profile.route) {
                ProfileScreen(
                    navController = navController
                )
            }
            authNavGraph(navController = navController)
            // Personality
            composable(Screen.Personality.route) {
                PersonalityScreen(
                    navController = navController,
                )
            }
            composable(Screen.PersonalityMainTest.route) {
                PersonalityMainTestScreen(
                    navController = navController,
                )
            }
            composable(Screen.PersonalityResult.route) {
                PersonalityResultScreen()
            }
            //Profile
            composable(Screen.ProfileEdit.route){
                ProfileEditScreen(navController)
            }
            composable(Screen.Pricing.route){
                PricingScreen()
            }
        }
    }
}



@Composable
private fun BottomBar(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        modifier = modifier
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        val navigationItems = listOf(
            NavigationItem(
                title = "Home",
                icon = Icons.Default.Home,
                screen = Screen.Home
            ),
            NavigationItem(
                title = "Wishlist",
                icon = Icons.Default.ShoppingCart,
                screen = Screen.Wishlist
            ),
            NavigationItem(
                title ="Profile",
                icon = Icons.Default.AccountCircle,
                screen = Screen.Profile
            ),
        )
        NavigationBar() {
            navigationItems.map { item ->
                NavigationBarItem(
                    icon = {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.title
                        )
                    },
                    label = { Text(item.title) },
                    selected = currentRoute == item.screen.route,
                    onClick = {
                        navController.navigate(item.screen.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            restoreState = true
                            launchSingleTop = true
                        }
                    }
                )
            }
        }
    }
}