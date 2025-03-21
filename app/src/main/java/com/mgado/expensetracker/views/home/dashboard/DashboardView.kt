package com.mgado.expensetracker.views.home.dashboard

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import com.mgado.expensetracker.R
import com.mgado.expensetracker.components.SimpleLineChart
import com.mgado.expensetracker.models.dummyTransactions
import com.mgado.expensetracker.utils.getDateTimeFormatted
import com.mgado.expensetracker.views.auth.widgets.ToggleVisibility
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.TextButton
import androidx.compose.ui.Alignment
import com.mgado.expensetracker.components.CircularIcon
import com.mgado.expensetracker.components.SectionHeader
import com.mgado.expensetracker.components.TransactionsList
import com.mgado.expensetracker.navigation.HomeScreens

@Composable
fun DashboardView(modifier: Modifier = Modifier, navController: NavHostController){
    Column(modifier = modifier.padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.Top) {
        DashboardHeader()
        Spacer(modifier = Modifier.height(12.dp))
        SectionHeader(label = "Quick Actions", icon = R.drawable.add_task)
        Spacer(modifier = Modifier.height(16.dp))
        Row(modifier = Modifier.fillMaxWidth(),horizontalArrangement = Arrangement.SpaceBetween) {
            ActionWidget(label = "Banks", icon = R.drawable.account_balance) {
                navController.navigate(HomeScreens.BanksScreen.name)
            }
            ActionWidget(label = "Wallets", icon = R.drawable.account_balance_wallet) {
                navController.navigate(HomeScreens.WalletsScreen.name)
            }
            ActionWidget(label = "Savings", icon = R.drawable.savings) {
                navController.navigate(HomeScreens.SavingsScreen.name)
            }
            ActionWidget(label = "Budget\nPlanner", icon = R.drawable.credit_card_heart) {
                navController.navigate(HomeScreens.BudgetScreen.name)
            }
            ActionWidget(label = "Investment\nGuide", icon = R.drawable.book) {
                navController.navigate(HomeScreens.InvestmentScreen.name)
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically) {
            SectionHeader(label = "Recent Transactions", icon = R.drawable.receipt_long)
            TextButton(onClick = {
                navController.navigate(HomeScreens.TransactionsScreen.name){
                    popUpTo(HomeScreens.DashboardScreen.name){
                        inclusive = false
                    }
                }
            },
                colors = ButtonDefaults.textButtonColors(contentColor = colorResource(R.color.green))) {
                Text("View All")
            }
        }
        TransactionsList(transactions = dummyTransactions.subList(0, 7))
    }
}



@Composable
fun ActionWidget(label: String, @DrawableRes icon: Int, onClick: ()-> Unit){
    Column(modifier = Modifier.clickable{
        onClick.invoke()
    }, horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        CircularIcon(icon = icon)
        Text(label, style = MaterialTheme.typography.labelSmall, textAlign = TextAlign.Center)
    }
}

@Composable
fun DashboardHeader() {
    val username = FirebaseAuth.getInstance().currentUser?.displayName
    val lastSignInTimestamp = FirebaseAuth.getInstance().currentUser?.metadata?.lastSignInTimestamp
    val balanceVisibility = rememberSaveable {
        mutableStateOf(false)
    }

    Card(modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = colorResource(R.color.shadow_green))) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End) {
                Column(horizontalAlignment = Alignment.End) {
                    Text("Good Morning" + if (username.isNullOrEmpty()) "" else ", $username",
                        style = MaterialTheme.typography.titleSmall)
                    Text("Last logged in ${getDateTimeFormatted(lastSignInTimestamp)}",
                        style = MaterialTheme.typography.bodySmall)
                }
                Spacer(modifier = Modifier.width(6.dp))
                Image(painter = painterResource(R.drawable.account_circle),
                    contentDescription = "account circle",
                    modifier = Modifier
                        .height(31.5.dp)
                        .width(31.5.dp))
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.Bottom) {
                Column {
                    Text("Mohni Status", style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.height(8.dp))
                    BalanceInfo(label = "Available Balance",
                        info = "1,000.00",
                        visibility = balanceVisibility)
                    Spacer(modifier = Modifier.height(16.dp))
                }
                ToggleVisibility(visibility = balanceVisibility)
                SimpleLineChart(modifier = Modifier.size(138.5.dp, 80.dp))
            }
            Row {
                BalanceInfo(label = "Actual Balance",
                    info = "2,000.00",
                    visibility = balanceVisibility)

                Spacer(modifier = Modifier.width(16.dp))
                BalanceInfo(label = "Expense",
                    info = "1,500.00",
                    visibility = balanceVisibility)
            }
        }
    }
}

@Composable
fun BalanceInfo(label: String, info: String, visibility: MutableState<Boolean>) {
    Column {
        Text(label, style = MaterialTheme.typography.labelSmall)
        Spacer(modifier = Modifier.height(4.dp))
        Row {
            Text("NPR " + if (visibility.value) info else "*,***.**",
                style = MaterialTheme.typography.bodyLarge)
        }
    }
}
