package com.neilb.nonogram.presentation.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.neilb.nonogram.common.NavDestinations
import com.neilb.nonogram.presentation.ui.views.main.MainScreen
import com.neilb.nonogram.presentation.ui.views.main.MainViewModel
import com.neilb.nonogram.presentation.ui.views.menu.MenuScreen

@Composable
fun NavigationView() {
    val navController = rememberNavController()
    val mainViewModel = hiltViewModel<MainViewModel>()

    NavHost(navController = navController, startDestination = NavDestinations.MENU_SCREEN) {
        composable(NavDestinations.MENU_SCREEN) {
            MenuScreen(navController, mainViewModel)
        }

        composable(NavDestinations.MAIN_SCREEN) {
            MainScreen(navController, mainViewModel)
        }
    }
}