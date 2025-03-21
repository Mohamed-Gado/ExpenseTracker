package com.mgado.expensetracker.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.mgado.expensetracker.views.home.investment.InvestmentView
import com.mgado.expensetracker.views.home.banks.BanksView
import com.mgado.expensetracker.views.home.budget.BudgetView
import com.mgado.expensetracker.views.home.calendar.CalendarView
import com.mgado.expensetracker.views.home.dashboard.DashboardView
import com.mgado.expensetracker.views.home.ocr_scanner.OcrScannerView
import com.mgado.expensetracker.views.home.savings.SavingsView
import com.mgado.expensetracker.views.home.statistics.StatisticsView
import com.mgado.expensetracker.views.home.transactions.TransactionsView
import com.mgado.expensetracker.views.home.wallets.WalletsView

@Composable
fun NavBarNavigation(modifier: Modifier, navController: NavHostController, currentItem: MutableState<Int>){

    NavHost(navController = navController, startDestination = HomeScreens.DashboardScreen.name, modifier){
        composable(HomeScreens.DashboardScreen.name) {
            currentItem.value = 0
            DashboardView(navController = navController)
        }
        composable(HomeScreens.StatisticsScreen.name) {
            currentItem.value = 3
            StatisticsView(navController = navController)
        }
        composable(HomeScreens.TransactionsScreen.name) {
            currentItem.value = 1
            TransactionsView(navController = navController)
        }
        composable(HomeScreens.CalendarScreen.name) {
            currentItem.value = 4
            CalendarView()
        }
        composable(HomeScreens.SavingsScreen.name) {
            SavingsView(navController)
        }
        composable(HomeScreens.BanksScreen.name) {
            BanksView(navController)
        }
        composable(HomeScreens.WalletsScreen.name) {
            WalletsView(navController)
        }
        composable(HomeScreens.InvestmentScreen.name) {
            InvestmentView(navController)
        }
        composable(HomeScreens.BudgetScreen.name) {
            BudgetView(navController)
        }
        composable(HomeScreens.OcrScannerScreen.name) {
            OcrScannerView(navController)
        }
    }
}



