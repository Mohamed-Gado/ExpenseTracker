package com.mgado.expensetracker.views.home.budget

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.mgado.expensetracker.components.AddNewItemWidget
import com.mgado.expensetracker.components.AddPlanDialog
import com.mgado.expensetracker.components.CustomAlertDialog
import com.mgado.expensetracker.components.SectionHeader
import com.mgado.expensetracker.components.SubViewBaseWidget
import com.mgado.expensetracker.models.BudgetPlan
import com.mgado.expensetracker.models.dummyPlans

@Composable
fun BudgetView(navController: NavHostController) {
    val openAddPlanDialog = remember { mutableStateOf(false) }
    val openAlertDialog = remember { mutableStateOf(false) }
    SubViewBaseWidget(navController = navController) {
        SectionHeader(label = "Budget Plans",
            icon = R.drawable.monetization_on,
            modifier = Modifier
                .padding(vertical = 8.dp)
                .align(Alignment.Start))
        LazyColumn(modifier = Modifier.weight(1f).padding(bottom = 12.dp, top = 12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(count = dummyPlans.size) {
                PlansListItem(dummyPlans[it])
            }
        }
        AddNewItemWidget(title = "Add another plan") { openAddPlanDialog.value = true }
        when {
            openAddPlanDialog.value -> {
                AddPlanDialog(modifier = Modifier.padding(bottom = 80.dp),
                    showAlert = {
                        openAlertDialog.value = true
                    },
                    onApproveRequest = {
                        openAddPlanDialog.value = false
                    }) {
                    openAddPlanDialog.value = false
                }
            }
        }
        when {
            openAlertDialog.value -> {
                CustomAlertDialog(modifier = Modifier.padding(bottom = 80.dp),
                    title = "Are you sure?",
                    subtitle = "You have unsaved changes. Your date will be lost.",
                    onApproveRequest = {
                        openAddPlanDialog.value = false
                        openAlertDialog.value = false
                    }) {
                    openAlertDialog.value = false
                }
            }
        }
    }
}

@Composable
fun PlansListItem(plan: BudgetPlan) {
    Surface(modifier = Modifier
        .fillMaxWidth()
        .height(56.dp),
        shape = RoundedCornerShape(5.dp),
        color = colorResource(R.color.background_green)) {
        Row(modifier = Modifier
            .fillMaxSize()
            .padding(start = 10.dp, end = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically) {
            Column {
                Text(plan.name, style = MaterialTheme.typography.bodyLarge, maxLines = 1)
                Text(plan.date, style = MaterialTheme.typography.labelSmall)
            }
            Text("NPR ${plan.total}",
                style = MaterialTheme.typography.displaySmall,
                color = colorResource(R.color.orange))
        }
    }}