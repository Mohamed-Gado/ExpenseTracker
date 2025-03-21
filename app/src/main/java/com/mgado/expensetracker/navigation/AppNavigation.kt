package com.mgado.expensetracker.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mgado.expensetracker.SplashScreen
import com.mgado.expensetracker.views.auth.LoginView
import com.mgado.expensetracker.views.auth.RegisterView
import com.mgado.expensetracker.views.home.HomeView

@Composable
fun AppNavigation(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = AppScreens.SplashScreen.name){
        composable(AppScreens.SplashScreen.name) {
            SplashScreen(navController = navController)
        }
        composable(AppScreens.LoginScreen.name) {
            LoginView(navController)
        }
        composable(AppScreens.RegisterScreen.name) {
            RegisterView(navController)
        }
        composable(AppScreens.HomeScreen.name) {
            HomeView(navController)
        }
    }
}

