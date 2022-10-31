package com.example.liftinglogs

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable(route = "home") {
            DateList(goToWorkout = { navController.navigate("workout") })
        }
        composable(route = "workout") {
            WorkoutsScreen(goBackHome = { navController.navigateUp()})
        }
    }
}