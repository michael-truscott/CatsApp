package com.truscorp.catsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.truscorp.catsapp.ui.home.HomeScreen
import com.truscorp.catsapp.ui.home.HomeViewModel
import com.truscorp.catsapp.ui.tags.TagsScreen
import com.truscorp.catsapp.ui.tags.TagsViewModel
import com.truscorp.catsapp.ui.theme.CatsAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CatsAppTheme {
                AppScaffold()
            }
        }
    }
}

sealed class RootScreen(val route: String) {
    object Home : RootScreen("home_root")
    object Tags : RootScreen("tags_root")
    object Favourites : RootScreen("favourites_root")
}

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object HomeDetails : Screen("home_details/{id}")
    object Tags : Screen("tags")
    object Favourites : Screen("favourites")

}

data class BottomTabItem(
    val screen: RootScreen,
    val name: String,
    @DrawableRes val iconId: Int
)

val bottomTabItems = listOf(
    BottomTabItem(RootScreen.Home, "Home", R.drawable.icon_home),
    BottomTabItem(RootScreen.Tags, "Tags", R.drawable.icon_tag),
    BottomTabItem(RootScreen.Favourites, "Favourites", R.drawable.icon_favorite),
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppScaffold() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

//    Log.d("MainActivity", "hierarchy:")
//    currentDestination?.hierarchy?.forEach { Log.d("MainActivity", it.route.toString()) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Scaffold(
            bottomBar = {
                CatsAppBottomBar(
                    items = bottomTabItems,
                    navController = navController,
                    currentDestination = currentDestination
                )
            }
        ) { paddingValues ->
            AppNavHost(
                modifier = Modifier.padding(paddingValues),
                navController = navController
            )
        }
    }
}

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = RootScreen.Home.route,
        modifier = modifier
    ) {
        navigation(route = RootScreen.Home.route, startDestination = Screen.Home.route) {
            composable(Screen.Home.route) {
                val viewModel: HomeViewModel = hiltViewModel()
                HomeScreen(viewModel = viewModel, navController = navController)
            }
            composable(
                Screen.HomeDetails.route,
                arguments = listOf(
                    navArgument("id") {
                        type = NavType.StringType
                    }
                )
            ) {
                Text(text = "Details")
            }
        }

        navigation(route = RootScreen.Tags.route, startDestination = Screen.Tags.route) {
            composable(Screen.Tags.route) {
                val viewModel: TagsViewModel = hiltViewModel()
                TagsScreen(viewModel = viewModel, navController = navController)
            }
        }

        navigation(route = RootScreen.Favourites.route, startDestination = Screen.Favourites.route) {
            composable(Screen.Favourites.route) {
                Text(text = "Favourites")
            }
        }

    }
}

@Composable
fun CatsAppBottomBar(
    modifier: Modifier = Modifier,
    items: List<BottomTabItem>,
    navController: NavHostController,
    currentDestination: NavDestination?
) {
    val selectedItemIndex = items.indexOfFirst { item -> currentDestination?.hierarchy?.any { it.route == item.screen.route } == true }
    CatsAppBottomBarStateless(
        modifier,
        items = items,
        selectedItemIndex = selectedItemIndex,
        onItemClicked = { item ->
            navController.navigate(item.screen.route) {
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }
        }
    )

}

@Composable
fun CatsAppBottomBarStateless(
    modifier: Modifier = Modifier,
    items: List<BottomTabItem>,
    selectedItemIndex: Int,
    onItemClicked: (BottomTabItem) -> Unit
) {
    BottomAppBar(modifier) {
        items.forEachIndexed { index, info ->
            NavigationBarItem(
                selected = index == selectedItemIndex,
                onClick = { onItemClicked(info) },
                icon = {
                    Icon(
                        painter = painterResource(id = info.iconId),
                        contentDescription = info.name
                    )
                },
                label = { Text(text = info.name) }
            )
        }
    }
}