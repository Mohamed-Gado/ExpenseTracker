package com.mgado.expensetracker.views.home.banks

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.mgado.expensetracker.R
import com.mgado.expensetracker.components.AddBankDialog
import com.mgado.expensetracker.components.AddNewItemWidget
import com.mgado.expensetracker.components.CustomAlertDialog
import com.mgado.expensetracker.components.SectionHeader
import com.mgado.expensetracker.components.SimpleLineChart
import com.mgado.expensetracker.components.SubViewBaseWidget
import com.mgado.expensetracker.models.Bank
import com.mgado.expensetracker.models.dummyBanks
import com.mgado.expensetracker.views.home.widgets.BalanceWidget

@Composable
fun BanksView(navController: NavHostController) {
    val openAddBankDialog = remember { mutableStateOf(false) }
    val openAlertDialog = remember { mutableStateOf(false) }
    SubViewBaseWidget(navController = navController) {
        SectionHeader(label = "Banks",
            icon = R.drawable.account_balance,
            modifier = Modifier
                .padding(vertical = 8.dp)
                .align(Alignment.Start))
        LazyColumn(modifier = Modifier.weight(1f).padding(bottom = 12.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(count = dummyBanks.size) {
                BanksListItem(dummyBanks[it])
            }
        }
        AddNewItemWidget(title = "Add another bank") { openAddBankDialog.value = true }
        when {
            openAddBankDialog.value -> {
                AddBankDialog(modifier = Modifier.padding(bottom = 80.dp),
                    showAlert = {
                        openAlertDialog.value = true
                    },
                    onApproveRequest = {
                        openAddBankDialog.value = false
                    }) {
                    openAddBankDialog.value = false
                }
            }
        }
        when {
            openAlertDialog.value -> {
                CustomAlertDialog(modifier = Modifier.padding(bottom = 80.dp),
                    title = "Are you sure?",
                    subtitle = "You have unsaved changes. Your date will be lost.",
                    onApproveRequest = {
                        openAddBankDialog.value = false
                        openAlertDialog.value = false
                    }) {
                    openAlertDialog.value = false
                }
            }
        }
    }
}

@Composable
fun BanksListItem(bank: Bank) {
    Surface(modifier = Modifier
        .fillMaxWidth()
        .wrapContentHeight(),
        shape = RoundedCornerShape(5.dp),
        color = colorResource(R.color.shadow_green)) {
        Row(modifier = Modifier.padding(4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween) {
            BankNameWidget(shortName = bank.shortName, name = bank.name)
            BalanceWidget(balance = bank.userBalance)
            SimpleLineChart(modifier = Modifier
                .height(50.dp)
                .width(60.dp)
                .padding(end = 6.dp, top = 6.dp)
                .align(Alignment.CenterVertically))
        }
    }
}

@Composable
fun BankNameWidget(shortName: String, name: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Card(modifier = Modifier
            .width(44.dp)
            .height(44.dp)
            .padding(bottom = 4.dp),
            shape = CircleShape,
            colors = CardDefaults.cardColors(containerColor = colorResource(R.color.gray))
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(shortName, style = MaterialTheme.typography.bodyLarge)
            }
        }
        Text(name, style = MaterialTheme.typography.labelSmall)
    }
}