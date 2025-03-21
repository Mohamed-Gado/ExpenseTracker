package com.mgado.expensetracker.views.home.widgets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.mgado.expensetracker.R
import com.mgado.expensetracker.navigation.HomeScreens
import com.mgado.expensetracker.views.home.transactions.ToggleButton
import java.util.Locale


@Composable
fun ToggleHeaderWidget(selectedDateOption: MutableState<String>, navController: NavHostController) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Spacer(modifier = Modifier.width(20.dp))
        ToggleButton(
            selectedOption = selectedDateOption,
            firstValue = "Monthly",
            secondValue = "Yearly"
        )
        Icon(painterResource(id = R.drawable.scanner),
            modifier = Modifier
                .height(20.dp)
                .width(20.dp)
                .clickable {
                    navController.navigate(HomeScreens.OcrScannerScreen.name)
                },
            tint = colorResource(R.color.black),
            contentDescription = "calendar icon")
    }
}



@Composable
fun BalanceWidget(balance: Double) {
    val formattedBalance = String.format(Locale.US, "%.2f", balance)
    Column(modifier = Modifier.fillMaxHeight(),
        verticalArrangement = Arrangement.Center) {
        Text("Available Balance", style = MaterialTheme.typography.labelSmall)
        Text("NPR $formattedBalance", style = MaterialTheme.typography.bodyLarge)
    }
}