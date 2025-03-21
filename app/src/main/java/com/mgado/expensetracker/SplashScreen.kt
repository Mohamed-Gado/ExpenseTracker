package com.mgado.expensetracker

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import com.mgado.expensetracker.navigation.AppScreens

@Composable
fun SplashScreen(navController: NavHostController) {
    LaunchedEffect(key1 = true){
        navController.navigate(if(FirebaseAuth.getInstance().currentUser != null) AppScreens.HomeScreen.name else AppScreens.LoginScreen.name){
            popUpTo(AppScreens.SplashScreen.name){
                inclusive = true
            }
        }
    }

    Column(verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(16.dp)) {
    }
}
