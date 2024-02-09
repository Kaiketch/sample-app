package com.redpond.sampleapp.ui.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.redpond.sampleapp.ui.home.HomeRoute
import com.redpond.sampleapp.ui.settings.SettingsRoute

const val MAIN_GRAPH_ROUTE = "main_graph"
const val HOME_ROUTE = "home"
const val SETTINGS_ROUTE = "settings"

@Composable
fun MainRoute() {
    val navController = rememberNavController()
    MainScreen(navController = navController)
}

@Composable
fun MainScreen(
    navController: NavHostController
) {

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            BottomNavigation(
                currentRoute = navController.currentDestination?.route.orEmpty(),
                onNavItemClick = { route ->
                    navController.navigate(route)
                }
            )
        }
    ) { paddingValue ->
        NavHost(
            modifier = Modifier.padding(paddingValue),
            navController = navController,
            startDestination = HOME_ROUTE
        ) {
            composable(HOME_ROUTE) {
                HomeRoute()
            }
            composable(SETTINGS_ROUTE) {
                SettingsRoute()
            }
        }
    }
}

@Composable
fun BottomNavigation(
    currentRoute: String,
    onNavItemClick: (String) -> Unit = {}
) {
    NavigationBar {
        NavigationBarItem(
            selected = currentRoute == HOME_ROUTE,
            onClick = { onNavItemClick(HOME_ROUTE) },
            label = { Text(text = HOME_ROUTE) },
            icon = { Icon(Icons.Default.Home, HOME_ROUTE) }
        )
        NavigationBarItem(
            selected = currentRoute == SETTINGS_ROUTE,
            onClick = { onNavItemClick(SETTINGS_ROUTE) },
            label = { Text(text = SETTINGS_ROUTE) },
            icon = { Icon(Icons.Default.Settings, SETTINGS_ROUTE) }
        )
    }
}
