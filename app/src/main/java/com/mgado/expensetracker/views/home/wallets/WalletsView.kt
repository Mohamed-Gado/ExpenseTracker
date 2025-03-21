package com.mgado.expensetracker.views.home.wallets

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.mgado.expensetracker.R
import com.mgado.expensetracker.components.AddNewItemWidget
import com.mgado.expensetracker.components.AddWalletDialog
import com.mgado.expensetracker.components.CustomAlertDialog
import com.mgado.expensetracker.components.SectionHeader
import com.mgado.expensetracker.components.SimpleLineChart
import com.mgado.expensetracker.components.SubViewBaseWidget
import com.mgado.expensetracker.models.Wallet
import com.mgado.expensetracker.models.dummyWallets
import com.mgado.expensetracker.views.home.widgets.BalanceWidget


@Composable
fun WalletsView(navController: NavHostController) {
    val openAddWalletDialog = remember { mutableStateOf(false) }
    val openAlertDialog = remember { mutableStateOf(false) }
    SubViewBaseWidget(navController = navController) {
        SectionHeader(label = "Wallets",
            icon = R.drawable.account_balance,
            modifier = Modifier
                .padding(vertical = 8.dp)
                .align(Alignment.Start))
        LazyColumn(modifier = Modifier.weight(1f).padding(bottom = 12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(count = dummyWallets.size) {
                WalletsListItem(dummyWallets[it])
            }
        }
        AddNewItemWidget(title = "Add another Wallet") { openAddWalletDialog.value = true }
        when {
            openAddWalletDialog.value -> {
                AddWalletDialog(modifier = Modifier.padding(bottom = 80.dp),
                    showAlert = {
                        openAlertDialog.value = true
                    },
                    onApproveRequest = {
                        openAddWalletDialog.value = false
                    }) {
                    openAddWalletDialog.value = false
                }
            }
        }
        when {
            openAlertDialog.value -> {
                CustomAlertDialog(modifier = Modifier.padding(bottom = 80.dp),
                    title = "Are you sure?",
                    subtitle = "You have unsaved changes. Your date will be lost.",
                    onApproveRequest = {
                        openAddWalletDialog.value = false
                        openAlertDialog.value = false
                    }) {
                    openAlertDialog.value = false
                }
            }
        }
    }
}
@Composable
fun WalletsListItem(wallet: Wallet) {
    Surface(modifier = Modifier
        .fillMaxWidth()
        .wrapContentHeight(),
        shape = RoundedCornerShape(5.dp),
        color = colorResource(R.color.shadow_green)) {
        Row(modifier = Modifier.padding(vertical = 4.dp, horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween) {
            WalletLogoWidget(logo = wallet.image,
                name = wallet.name,
                backgroundColor = wallet.backgroundColor)
            BalanceWidget(balance = wallet.userBalance)
            SimpleLineChart(modifier = Modifier
                .height(50.dp)
                .width(60.dp)
                .padding(end = 6.dp, top = 6.dp)
                .align(Alignment.CenterVertically))
        }
    }
}

@Composable
fun WalletLogoWidget(logo: Int, backgroundColor: Int, name: String) {
    Column(modifier = Modifier.width(70.dp),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Card(modifier = Modifier.wrapContentSize(),
            shape = CircleShape,
            colors = CardDefaults.cardColors(containerColor = colorResource(backgroundColor))
        ) {
            Image(painter = painterResource(logo),
                contentDescription = null,
                modifier = Modifier
                    .width(44.dp)
                    .height(44.dp)
                    .padding(10.dp),
                contentScale = ContentScale.Fit)

        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(name, style = MaterialTheme.typography.labelSmall)
    }
}
