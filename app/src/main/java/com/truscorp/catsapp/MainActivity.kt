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
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
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

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Favourites : Screen("favourites")
}

data class BottomTabItem(
    val screen: Screen,
    val name: String,
    @DrawableRes val iconId: Int
)

val bottomTabItems = listOf(
    BottomTabItem(Screen.Home, "Home", R.drawable.icon_home),
    BottomTabItem(Screen.Favourites, "Favourites", R.drawable.icon_favorite),
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppScaffold() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Scaffold(
            bottomBar = {
                CatsAppBottomBar(
                    items = bottomTabItems,
                    onItemClicked = { item ->
                        navController.navigate(item.screen.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    isItemSelected = { item ->
                        currentDestination?.hierarchy?.any { it.route == item.screen.route } == true
                    }
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
        startDestination = "home",
        modifier = modifier
    ) {
        composable(Screen.Home.route) {
            Text(text = "Home")
        }
        composable(Screen.Favourites.route) {
            Text(text = "Favourites")
        }
    }
}

@Composable
fun CatsAppBottomBar(
    modifier: Modifier = Modifier,
    items: List<BottomTabItem>,
    isItemSelected: (BottomTabItem) -> Boolean,
    onItemClicked: (BottomTabItem) -> Unit
) {
    BottomAppBar(modifier) {
        items.forEach { info ->
            NavigationBarItem(
                selected = isItemSelected(info),
                onClick = { onItemClicked(info) },
                icon = { Icon(painter = painterResource(id = info.iconId), contentDescription = info.name) },
                label = { Text(text = info.name) }
            )
        }
    }
}
