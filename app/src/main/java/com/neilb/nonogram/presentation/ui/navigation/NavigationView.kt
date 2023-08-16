package com.neilb.nonogram.presentation.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.neilb.nonogram.common.NavDestinations
import com.neilb.nonogram.presentation.ui.views.collections.CollectionScreen
import com.neilb.nonogram.presentation.ui.views.create_nonogram.CreateNonogramScreen
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

        composable(NavDestinations.CREATE_NONOGRAM_SCREEN) {
            CreateNonogramScreen(navController, mainViewModel)
        }

        composable("${NavDestinations.COLLECTIONS_SCREEN}/{public}") {
            val isPublic = it.arguments?.getString("public") == "public"
            CollectionScreen(navController, mainViewModel, isPublic)
        }
    }
}