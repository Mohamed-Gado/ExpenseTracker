package com.mgado.expensetracker.views.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.mgado.expensetracker.components.AddTransactionDialog
import com.mgado.expensetracker.components.DatePickerModal
import com.mgado.expensetracker.navigation.HomeScreens
import com.mgado.expensetracker.navigation.NavBarNavigation
import com.mgado.expensetracker.views.home.widgets.BottomNavigation

@Composable
fun HomeView(navController: NavHostController) {
    val currentItem: MutableState<Int> = remember {
        mutableIntStateOf(0)
    }
    val homeNavController = rememberNavController()
    val openAddTransactionDialog = remember { mutableStateOf(false) }
    val openDatePickerDialog = remember { mutableStateOf(false) }

    Scaffold(
        bottomBar = {
            BottomNavigation(
                currentItem = currentItem.value,
                onItemClick = {
                    if(it == currentItem.value && navController.previousBackStackEntry != null){
                        return@BottomNavigation
                    }
                    if(it != 2) {
                        currentItem.value = it
                        when (it){
                            0 -> homeNavController.navigate(HomeScreens.DashboardScreen.name){
                                popUpTo(HomeScreens.DashboardScreen.name){ inclusive = true }
                            }
                            1 -> homeNavController.navigate(HomeScreens.TransactionsScreen.name){
                                popUpTo(HomeScreens.DashboardScreen.name){ inclusive = false }
                            }
                            3 -> homeNavController.navigate(HomeScreens.StatisticsScreen.name){
                                popUpTo(HomeScreens.DashboardScreen.name){ inclusive = false }
                            }
                            4 -> homeNavController.navigate(HomeScreens.CalendarScreen.name){
                                popUpTo(HomeScreens.DashboardScreen.name){ inclusive = false }
                            }
                        }
                    }
                    else {
                        openAddTransactionDialog.value = true
                    }
                })
        }
    ) { innerPadding ->
        NavBarNavigation(modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding),
            navController = homeNavController,
            currentItem = currentItem)
        when {
            openAddTransactionDialog.value -> {
                AddTransactionDialog(modifier = Modifier.padding(bottom = innerPadding.calculateBottomPadding()), openDatePickerDialog) {
                    openAddTransactionDialog.value = false
                }
            }
        }
        when {
            openDatePickerDialog.value -> {
                DatePickerModal(onDateSelected = {}) {
                    openDatePickerDialog.value = false
                }
            }
        }
    }
}

