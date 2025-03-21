package com.mgado.expensetracker.views.home.savings

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.mgado.expensetracker.R
import com.mgado.expensetracker.components.DepositsList
import com.mgado.expensetracker.components.SectionHeader
import com.mgado.expensetracker.components.SubViewBaseWidget
import com.mgado.expensetracker.models.dummyDeposits

@Composable
fun SavingsView(navController: NavHostController) {
    SubViewBaseWidget(navController) {
        SectionHeader(label = "Savings",
            icon = R.drawable.account_balance_wallet,
            modifier = Modifier.padding(vertical = 8.dp).align(Alignment.Start))
        Image(painter = painterResource(R.drawable.savings),
            contentDescription = null,
            modifier = Modifier
                .height(100.dp)
                .width(100.dp)
                .padding(top = 16.dp, bottom = 12.dp))

        Text("Total", style = MaterialTheme.typography.bodyMedium)
        Text("NPR 30,000.00", style = MaterialTheme.typography.titleLarge)
        Surface(modifier = Modifier.height(45.dp),
            shape = RoundedCornerShape(5.dp),
            color = colorResource(R.color.background_green)) {
            Row(modifier = Modifier.padding(horizontal = 4.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically) {
                Column {
                    Text("Deposits",
                        style = MaterialTheme.typography.labelSmall,
                        color = colorResource(R.color.chartreuse))
                    Text("NPR 20,000.00",
                        style = MaterialTheme.typography.titleSmall,
                        color = colorResource(R.color.chartreuse))
                }
                Spacer(modifier = Modifier.width(30.dp))
                Column(horizontalAlignment = Alignment.End) {
                    Text("Interest",
                        style = MaterialTheme.typography.labelSmall,
                        color = colorResource(R.color.green))
                    Text("NPR 10,000.00",
                        style = MaterialTheme.typography.titleSmall,
                        color = colorResource(R.color.green))
                }
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        SectionHeader(label = "Recent Deposits", icon = R.drawable.receipt_long, modifier = Modifier.padding(vertical = 8.dp).align(Alignment.Start))
        DepositsList(deposits = dummyDeposits)
    }
}
