package com.mgado.expensetracker.components

import android.view.Gravity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddCircle
import androidx.compose.material.icons.rounded.RemoveCircle
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.DialogWindowProvider
import com.mgado.expensetracker.R
import com.mgado.expensetracker.models.dummyCategories
import com.mgado.expensetracker.models.dummyTransactionTypes

@Composable
fun BottomBasicDialog(modifier: Modifier,
                      height: Int,
                      onDismissRequest: () -> Unit,
                      content: @Composable () -> Unit){
    Dialog(onDismissRequest = onDismissRequest, properties = DialogProperties(usePlatformDefaultWidth = false)) {
        (LocalView.current.parent as DialogWindowProvider).apply {
            window.setGravity(Gravity.BOTTOM)
        }
        Card(modifier = modifier
            .fillMaxWidth()
            .padding(12.dp)
            .height(height.dp),
            colors = CardDefaults.cardColors(containerColor = colorResource(R.color.dialog_background)),
            shape = RoundedCornerShape(10.dp)){
            Column(modifier = Modifier
                .fillMaxSize()
                .padding(top = 12.dp),
                verticalArrangement = Arrangement.SpaceBetween) { content() }
        }
    }
}

@Composable
fun CustomAlertDialog(modifier: Modifier,
                      title: String,
                      subtitle: String,
                      onApproveRequest: ()->Unit,
                      onCancelRequest: () -> Unit){
    BottomBasicDialog(modifier = modifier, 175, onCancelRequest) {
        Column(modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally) {
            Text(title, style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.height(16.dp))
            Text(subtitle, style = MaterialTheme.typography.bodyMedium)
        }
        CustomDialogActionButtons(modifier = Modifier.padding(top = 8.dp),
            approveButtonLabel = "Yes",
            approveIsCritical = true,
            onCancelClicked = onCancelRequest,
            onApproveClicked = onApproveRequest)
    }
}

@Composable
fun AddPlanDialog(
    modifier: Modifier,
    showAlert: () -> Unit,
    onApproveRequest: () -> Unit,
    onDismissRequest: () -> Unit
) {
    val planName = remember { mutableStateOf("") }
    val total = remember { mutableStateOf("") }
    val planItemsCount = remember {
        mutableIntStateOf(0)
    }
    BottomBasicDialog(modifier.heightIn(230.dp, 430.dp),
        if(planItemsCount.intValue == 0) 230
        else (230 + planItemsCount.intValue * 35), {
        if(planName.value.isEmpty() &&
            total.value.isEmpty()){
            onDismissRequest.invoke()
        } else {
            showAlert.invoke()
        }
    }) {
        val infoModifier = Modifier.padding(horizontal = 10.dp)
        SectionHeader(modifier = infoModifier, label = "Make Budget Plan", icon = R.drawable.attach_money)
        InputField(modifier = infoModifier.height(35.dp),
            valueState = planName,
            labelId = "Plan name",
            textStyle = MaterialTheme.typography.labelSmall,
            enabled = true)
        PlanItems(modifier = infoModifier.fillMaxWidth().heightIn(50.dp, 200.dp),itemsCount = planItemsCount)
        InputField(modifier = infoModifier.height(35.dp),
            valueState = total,
            labelId = "Total",
            textStyle = MaterialTheme.typography.labelSmall,
            enabled = true)
        CustomDialogActionButtons(modifier = Modifier.padding(top = 8.dp),
            approveButtonLabel = "Add",
            onCancelClicked = {
                if(planName.value.isEmpty() &&
                    total.value.isEmpty()){
                    onDismissRequest.invoke()
                } else {
                    showAlert.invoke()
                }
            },
            onApproveClicked = onApproveRequest)
    }
}

@Composable
fun PlanItems(modifier: Modifier = Modifier, itemsCount: MutableState<Int>) {
    Surface(modifier = modifier,
        color = colorResource(R.color.shadow_green),
        shape = RoundedCornerShape(5.dp)){
        Column(modifier = Modifier.padding(4.dp)) {
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Items", style = MaterialTheme.typography.labelSmall)
                Icon(imageVector = Icons.Rounded.AddCircle,
                    contentDescription = null,
                    modifier = Modifier
                        .height(20.dp)
                        .width(20.dp)
                        .clickable{
                            itemsCount.value += 1
                    },
                    tint = colorResource(R.color.blue))
            }
            LazyColumn {
                items(itemsCount.value) {
                    PlanItemWidget {
                        itemsCount.value -= 1
                    }
                }
            }
        }
    }
}

@Composable
fun PlanItemWidget(removeItem: ()->Unit) {
    val screenWidth = LocalConfiguration.current.screenWidthDp
    val itemName = remember { mutableStateOf("") }
    val expanded = remember { mutableStateOf(false) }
    val category: MutableState<String?> = remember { mutableStateOf(null) }
    val amount = remember { mutableStateOf("") }
    Row (modifier = Modifier.fillMaxWidth().padding(bottom = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween){
        InputField(
            modifier = Modifier.height(29.dp).width((screenWidth/3.4).dp),
            valueState = itemName,
            labelId = "Name",
            textStyle = MaterialTheme.typography.labelSmall,
            enabled = true,
            backgroundColor = R.color.background_green,
        )
        DecoratedDropdownMenu(
            modifier = Modifier
                .height(29.dp).width((screenWidth/4.3).dp),
            expanded = expanded,
            placeholder = "Category",
            selectedItem = category,
            backgroundColor = R.color.background_green,
            options = dummyTransactionTypes.map { it })
        InputField(
            modifier = Modifier.height(29.dp).width((screenWidth/4.3).dp),
            valueState = amount,
            labelId = "Amount",
            textStyle = MaterialTheme.typography.labelSmall,
            backgroundColor = R.color.background_green,
            enabled = true
        )
        Icon(
            imageVector = Icons.Rounded.RemoveCircle,
            contentDescription = null,
            modifier = Modifier
                .height(18.dp)
                .width(18.dp)
                .clickable {
                    removeItem.invoke()
                },
            tint = colorResource(R.color.dark_red)
        )
    }
}

@Composable
fun AddWalletDialog(modifier: Modifier,
                  onApproveRequest: () -> Unit,
                  showAlert: () -> Unit,
                  onDismissRequest: () -> Unit){
    val walletName = remember { mutableStateOf("") }
    val accountHolderName = remember { mutableStateOf("") }
    val accountNumber = remember { mutableStateOf("") }
    val phoneNumber = remember { mutableStateOf("") }
    BottomBasicDialog(modifier, 230, {
        if(walletName.value.isEmpty() &&
            accountHolderName.value.isEmpty() &&
            accountNumber.value.isEmpty() &&
            phoneNumber.value.isEmpty()){
            onDismissRequest.invoke()
        } else {
            showAlert.invoke()
        }
    }) {
        val infoModifier = Modifier.padding(horizontal = 10.dp)
        SectionHeader(modifier = infoModifier, label = "Add Wallet", icon = R.drawable.payments)
        InputField(modifier = infoModifier.height(35.dp),
            valueState = walletName,
            labelId = "Wallet name",
            textStyle = MaterialTheme.typography.labelSmall,
            enabled = true)
        InputField(modifier = infoModifier.height(35.dp),
            valueState = phoneNumber,
            labelId = "Phone Number",
            textStyle = MaterialTheme.typography.labelSmall,
            enabled = true)
        InputField(modifier = infoModifier.height(35.dp),
            valueState = accountHolderName,
            labelId = "Account Holder's name",
            textStyle = MaterialTheme.typography.labelSmall,
            enabled = true)
        CustomDialogActionButtons(modifier = Modifier.padding(top = 8.dp),
            approveButtonLabel = "Validate",
            onCancelClicked = {
                if(walletName.value.isEmpty() &&
                    accountHolderName.value.isEmpty() &&
                    accountNumber.value.isEmpty() &&
                    phoneNumber.value.isEmpty()){
                    onDismissRequest.invoke()
                } else {
                    showAlert.invoke()
                }
            },
            onApproveClicked = onApproveRequest)
    }
}

@Composable
fun AddBankDialog(modifier: Modifier,
                  onApproveRequest: () -> Unit,
                  showAlert: () -> Unit,
                  onDismissRequest: () -> Unit){
    val bankName = remember { mutableStateOf("") }
    val accountHolderName = remember { mutableStateOf("") }
    val accountNumber = remember { mutableStateOf("") }
    val accountHolderPhoneNumber = remember { mutableStateOf("") }
    BottomBasicDialog(modifier, 275, {
        if(bankName.value.isEmpty() &&
            accountHolderName.value.isEmpty() &&
            accountNumber.value.isEmpty() &&
            accountHolderPhoneNumber.value.isEmpty()){
            onDismissRequest.invoke()
        } else {
            showAlert.invoke()
        }
    }) {
        val infoModifier = Modifier.padding(horizontal = 10.dp)
        SectionHeader(modifier = infoModifier, label = "Add Bank", icon = R.drawable.payments)
        InputField(modifier = infoModifier.height(35.dp),
            valueState = bankName,
            labelId = "Bank's name",
            textStyle = MaterialTheme.typography.labelSmall,
            enabled = true)
        InputField(modifier = infoModifier.height(35.dp),
            valueState = accountHolderName,
            labelId = "Account Holder's name",
            textStyle = MaterialTheme.typography.labelSmall,
            enabled = true)
        InputField(modifier = infoModifier.height(35.dp),
            valueState = accountNumber,
            labelId = "Account Number",
            textStyle = MaterialTheme.typography.labelSmall,
            enabled = true)
        InputField(modifier = infoModifier.height(35.dp),
            valueState = accountHolderPhoneNumber,
            labelId = "Account Holder's Phone Number",
            textStyle = MaterialTheme.typography.labelSmall,
            enabled = true)
        CustomDialogActionButtons(modifier = Modifier.padding(top = 8.dp),
            approveButtonLabel = "Validate",
            onCancelClicked = {
                if(bankName.value.isEmpty() &&
                    accountHolderName.value.isEmpty() &&
                    accountNumber.value.isEmpty() &&
                    accountHolderPhoneNumber.value.isEmpty()){
                    onDismissRequest.invoke()
                } else {
                    showAlert.invoke()
                }
            },
            onApproveClicked = onApproveRequest)
    }
}

@Composable
fun AddTransactionDialog(modifier: Modifier, openDatePickerDialog: MutableState<Boolean>, onDismissRequest: () -> Unit) {
    val name = remember { mutableStateOf("") }
    val amount = remember { mutableStateOf("") }
    val type: MutableState<String?> = remember { mutableStateOf(null) }
    var expandedType = remember { mutableStateOf(false) }
    var expandedCategory = remember { mutableStateOf(false) }
    val category: MutableState<String?> = remember { mutableStateOf(null) }
    val infoModifier = Modifier.padding(horizontal = 10.dp)

    BottomBasicDialog(modifier, 305, onDismissRequest) {
        SectionHeader(modifier = infoModifier, label = "Add Transaction", icon = R.drawable.payments)
        InputField(modifier = infoModifier.height(35.dp),
            valueState = name,
            labelId = "Transaction name",
            textStyle = MaterialTheme.typography.labelSmall,
            enabled = true)
        DecoratedDropdownMenu(
            modifier = infoModifier
                .fillMaxWidth()
                .height(29.dp),
            expanded = expandedType,
            placeholder = "Transaction type",
            selectedItem = type,
            options = dummyTransactionTypes.map { it })
        DecoratedDropdownMenu(
            modifier = infoModifier
                .fillMaxWidth()
                .height(29.dp),
            expanded = expandedCategory,
            placeholder = "Category",
            selectedItem = category,
            options = dummyCategories.map { it.title })
        Surface(modifier = infoModifier
            .fillMaxWidth()
            .height(29.dp)
            .clickable { openDatePickerDialog.value = true },
            color = colorResource(R.color.shadow_green),
            shape = RoundedCornerShape(5.dp)){
            Row(modifier = infoModifier
                .fillMaxWidth()
                .height(29.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Date", style = MaterialTheme.typography.bodySmall)
                Icon(painter = painterResource(R.drawable.calendar_add_on),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .width(19.dp)
                        .height(19.dp))
            }
        }
        InputField(modifier = infoModifier.height(35.dp),
            valueState = amount,
            labelId = "Amount",
            textStyle = MaterialTheme.typography.labelSmall,
            enabled = true)
        CustomDialogActionButtons(modifier = Modifier.padding(top = 8.dp),
            approveButtonLabel = "Add",
            onCancelClicked = onDismissRequest,
            onApproveClicked = {onDismissRequest.invoke()})
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerModal(
    onDateSelected: (Long?) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState()

    DatePickerDialog(
        onDismissRequest = onDismiss,
        colors = DatePickerDefaults.colors(containerColor = colorResource(R.color.dialog_background)),
        confirmButton = {
            TextButton(onClick = {
                onDateSelected(datePickerState.selectedDateMillis)
                onDismiss()
            }, colors = ButtonDefaults.textButtonColors(contentColor = colorResource(R.color.green))) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss, colors = ButtonDefaults.textButtonColors(contentColor = colorResource(R.color.green))) {
                Text("Cancel")
            }
        }
    ) {
        DatePicker(
            state = datePickerState,
            title = {},
            colors = DatePickerDefaults.colors(
                selectedYearContainerColor = colorResource(R.color.green),
                yearContentColor = colorResource(R.color.green),
                currentYearContentColor = colorResource(R.color.green),
                dayContentColor = colorResource(R.color.black),
                todayDateBorderColor = colorResource(R.color.green),
                todayContentColor = colorResource(R.color.green),
                selectedDayContainerColor = colorResource(R.color.green),
                containerColor = colorResource(R.color.dialog_background),
                headlineContentColor = colorResource(R.color.black),
                weekdayContentColor = colorResource(R.color.black),
                navigationContentColor = colorResource(R.color.black),
                subheadContentColor = colorResource(R.color.black),
                selectedYearContentColor = colorResource(R.color.white),
                selectedDayContentColor = colorResource(R.color.white),
                dividerColor = colorResource(R.color.black)))
    }
}