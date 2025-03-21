package com.mgado.expensetracker.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.rounded.AddCircle
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.KeyboardArrowUp
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.mgado.expensetracker.R
import com.mgado.expensetracker.models.Deposit
import com.mgado.expensetracker.models.Transaction
import com.mgado.expensetracker.utils.mapValueToDifferentRange

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputField(
    modifier: Modifier = Modifier,
    valueState: MutableState<String>,
    labelId: String,
    enabled: Boolean,
    isSingleLine: Boolean = true,
    textStyle: TextStyle = MaterialTheme.typography.bodyMedium,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    onAction: KeyboardActions = KeyboardActions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    backgroundColor: Int = R.color.shadow_green,
    trailingIcon: @Composable() (() -> Unit)? = null
) {
    val interactionSource = remember { MutableInteractionSource() }
    BasicTextField(
        value = valueState.value,
        onValueChange = {valueState.value = it},
        modifier =
            modifier
                .fillMaxWidth()
                .defaultMinSize(
                    minWidth = TextFieldDefaults.MinWidth,
                    minHeight = TextFieldDefaults.MinHeight),
        enabled = enabled,
        textStyle = textStyle,
        visualTransformation = visualTransformation,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = imeAction),
        keyboardActions = onAction,
        singleLine = isSingleLine,
        decorationBox =
            @Composable { innerTextField ->
                // places leading icon, text field with label and placeholder, trailing icon
                TextFieldDefaults.DecorationBox(
                    value = valueState.value,
                    visualTransformation = visualTransformation,
                    innerTextField = innerTextField,
                    placeholder = { Text(labelId, style = textStyle) },
                    trailingIcon = trailingIcon,
                    shape = RoundedCornerShape(5.dp),
                    singleLine = isSingleLine,
                    enabled = enabled,
                    interactionSource = interactionSource,
                    contentPadding = PaddingValues(start = 10.dp, end = 10.dp),
                    colors = OutlinedTextFieldDefaults.colors(focusedContainerColor = colorResource(backgroundColor),
                        unfocusedContainerColor = colorResource(backgroundColor),
                        disabledContainerColor = colorResource(backgroundColor),
                        unfocusedBorderColor = Color.Transparent,
                        focusedBorderColor = Color.Transparent,
                        disabledBorderColor = Color.Transparent))
            }
    )
}

@Composable
fun AppCustomLogo() {
    Box(contentAlignment = Alignment.BottomEnd) {
        Box(modifier = Modifier.padding(bottom = 30.dp, end = 20.dp)) {
            Image(
                painter = painterResource(R.drawable.left_polygon),
                contentDescription = "first polygon",
                modifier = Modifier
                    .height(203.dp)
                    .width(203.dp)
                    .padding(start = 20.dp),
            )
            Image(
                painter = painterResource(R.drawable.right_polygon),
                contentDescription = "third polygon",
                modifier = Modifier
                    .height(203.dp)
                    .width(203.dp),
            )
        }
        Box(contentAlignment = Alignment.CenterStart) {
            Image(
                painter = painterResource(R.drawable.left_polygon),
                contentDescription = "second polygon",
                modifier = Modifier
                    .height(203.dp)
                    .width(203.dp)
                    .padding(start = 20.dp),
            )
            Image(
                painter = painterResource(R.drawable.right_polygon),
                contentDescription = "forth polygon",
                modifier = Modifier
                    .height(203.dp)
                    .width(203.dp),
            )
            Image(
                painter = painterResource(R.drawable.logo),
                contentDescription = "forth polygon",
                modifier = Modifier
                    .height(123.dp)
                    .width(123.dp),
            )
        }
    }
}
data class Point(val x: Float, val y: Float)

@Composable
fun SimpleLineChart(modifier: Modifier) {
    val values = listOf(
        Point(0f, 1.2f),
        Point(1f, 1.9f),
        Point(1.5f, 2.9f),
        Point(2f, 2.8f),
        Point(2.5f, 2.7f),
        Point(3f, 2.6f),
        Point(3.5f, 2.8f),
        Point(4f, 3f),
        Point(4.5f, 3.3f),
        Point(5f, 3.1f),
        Point(5.5f, 2.9f),
        Point(6f, 3.7f),
        Point(6.5f, 3.9f),
        Point(7f, 4.8f),
        Point(7.5f, 5f),
        Point(8f, 5.3f),
        Point(8.5f, 6f))

    val minXValue = values.minOf { it.x }
    val maxXValue = values.maxOf { it.x }
    val minYValue = values.minOf { it.y }
    val maxYValue = values.maxOf { it.y }

    val chartColor = colorResource(R.color.green)

    Box(modifier = modifier
        .padding(bottom = 24.dp)
        .drawBehind {
            val pixelPoints = values.map {
                val x = it.x.mapValueToDifferentRange(
                    inMin = minXValue,
                    inMax = maxXValue,
                    outMin = 0f,
                    outMax = size.width
                )

                val y = it.y.mapValueToDifferentRange(
                    inMin = minYValue,
                    inMax = maxYValue,
                    outMin = size.height,
                    outMax = 0f
                )

                Point(x, y)
            }

            val path = Path()

            pixelPoints.forEachIndexed { index, point ->
                if (index == 0) {
                    path.moveTo(point.x, point.y)
                } else {
                    path.lineTo(point.x, point.y)
                }
            }

            drawPath(
                path,
                color = chartColor,
                style = Stroke(width = 3f)
            )
        })
}

@Composable
fun SectionHeader(modifier: Modifier = Modifier,
                  label: String,
                  labelStyle: TextStyle = MaterialTheme.typography.bodyLarge,
                  @DrawableRes icon: Int,
                  iconSize: Int = 20) {
    Row(verticalAlignment = Alignment.Bottom, modifier = modifier) {
        Icon(
            painter = painterResource(icon),
            contentDescription = null,
            modifier = Modifier
                .height(iconSize.dp)
                .width(iconSize.dp))
        Spacer(modifier = Modifier.width(8.dp))
        Text(label, style = labelStyle)
    }
}

@Composable
fun TransactionsList(transactions: List<Transaction>) {
    Surface(color = colorResource(R.color.light_green),
        shape = RoundedCornerShape(5.dp)) {
        LazyColumn(modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
            .background(colorResource(R.color.light_green))) {
            items(items = transactions) {
                TransactionItem(it)
            }
        }
    }
}

@Composable
fun DepositsList(deposits: List<Deposit>) {
    Surface(color = colorResource(R.color.light_green),
        shape = RoundedCornerShape(5.dp)) {
        LazyColumn(modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
            .background(colorResource(R.color.light_green))) {
            items(items = deposits) {
                DepositItem(it)
            }
        }
    }
}

@Composable
fun DepositItem(deposit: Deposit) {
    Surface(modifier = Modifier
        .fillMaxWidth()
        .height(45.dp)
        .padding(bottom = 10.dp),
        shape = RoundedCornerShape(5.dp),
        color = colorResource(R.color.background_green)) {
        Row(modifier = Modifier
            .fillMaxSize()
            .padding(start = 8.dp, end = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically) {
            Column {
                Text(deposit.label, style = MaterialTheme.typography.bodySmall, maxLines = 1)
                Text(deposit.date, style = MaterialTheme.typography.displaySmall)
            }
            Text(deposit.depositId, style = MaterialTheme.typography.displaySmall)
            Text("NPR ${deposit.price}",
                style = MaterialTheme.typography.displaySmall,
                color = colorResource(R.color.green))
        }
    }
}

@Composable
fun TransactionItem(transaction: Transaction) {
    Surface(modifier = Modifier
        .fillMaxWidth()
        .height(45.dp)
        .padding(bottom = 10.dp),
        shape = RoundedCornerShape(5.dp),
        color = colorResource(R.color.background_green)) {
        Row(modifier = Modifier
            .fillMaxSize()
            .padding(start = 8.dp, end = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically) {
            Column {
                Text(transaction.title, style = MaterialTheme.typography.bodySmall, maxLines = 1)
                Text(transaction.date, style = MaterialTheme.typography.displaySmall)
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Surface(
                    shape = CircleShape,
                    color = colorResource(transaction.category.color),
                    modifier = Modifier
                        .height(7.dp)
                        .width(7.dp)
                ) {}
                Spacer(modifier = Modifier.width(4.dp))
                Text(transaction.category.title, style = MaterialTheme.typography.displaySmall)
            }
            Text("NPR ${transaction.price}",
                style = MaterialTheme.typography.displaySmall,
                color = colorResource(R.color.red))
        }
    }
}

@Composable
fun CircularIcon(icon: Int) {
    Card(
        modifier = Modifier
            .width(51.dp)
            .height(51.dp)
            .padding(bottom = 4.dp),
        shape = CircleShape,
        colors = CardDefaults.cardColors(containerColor = colorResource(R.color.gray))
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(icon),
                contentDescription = null,
                modifier = Modifier
                    .width(31.dp)
                    .height(31.dp))
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun CustomDropdownMenu(modifier: Modifier = Modifier,
                       expanded: MutableState<Boolean>,
                       placeholder: String,
                       selectedItem: MutableState<String?>,
                       options: List<String>,
                       onSelected: (String) ->Unit = {}) {
    ExposedDropdownMenuBox(expanded = expanded.value, onExpandedChange = {}) {
        Row(
            modifier = modifier.clickable { expanded.value = !expanded.value },
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                selectedItem.value ?: placeholder,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier
                    .menuAnchor(MenuAnchorType.PrimaryEditable, expanded.value))
            Spacer(modifier = Modifier.width(2.dp))
            Icon(
                imageVector = Icons.Rounded.KeyboardArrowDown,
                contentDescription = null
            )
        }
        ExposedDropdownMenu(
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false }) {
            options.forEach {
                DropdownMenuItem(
                    text = { Text(it, style = MaterialTheme.typography.bodySmall) },
                    onClick = {
                        expanded.value = false
                        selectedItem.value = it
                        onSelected(it)
                    })
            }
        }
    }
}


@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun DecoratedDropdownMenu(modifier: Modifier = Modifier,
                          expanded: MutableState<Boolean>,
                          placeholder: String,
                          selectedItem: MutableState<String?>,
                          options: List<String>,
                          backgroundColor: Int = R.color.shadow_green,
                          onSelected: (String) ->Unit = {}) {
    Surface(
        modifier = modifier,
        color = colorResource(backgroundColor),
        shape = RoundedCornerShape(5.dp),
    ) {
        ExposedDropdownMenuBox(expanded = expanded.value, onExpandedChange = {}) {
            Row(
                modifier = modifier.clickable { expanded.value = !expanded.value },
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    selectedItem.value ?: placeholder,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier
                        .menuAnchor(MenuAnchorType.PrimaryEditable, expanded.value)
                )
                Icon(imageVector = if(expanded.value) Icons.Rounded.KeyboardArrowUp  else Icons.Rounded.KeyboardArrowDown,
                    contentDescription = null)
            }
            ExposedDropdownMenu(
                expanded = expanded.value,
                onDismissRequest = { expanded.value = false }) {
                options.forEach {
                    Column {
                        DropdownMenuItem(
                            modifier = modifier,
                            text = { Text(it, style = MaterialTheme.typography.bodySmall) },
                            onClick = {
                                expanded.value = false
                                selectedItem.value = it
                                onSelected(it)
                            })
                        if (it != options.last())
                        HorizontalDivider(thickness = 1.dp, color = Color.Black)
                    }
                }
            }
        }
    }
}

@Composable
fun CustomDialogActionButtons(modifier: Modifier,
                              cancelButtonLabel: String = "Cancel",
                              approveButtonLabel: String,
                              approveIsCritical: Boolean = false,
                              onCancelClicked: ()->Unit,
                              onApproveClicked: ()->Unit) {
    Column(modifier = modifier) {
        HorizontalDivider(thickness = 1.dp, color = Color.Black)
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            TextButton(
                onClick = onCancelClicked,
                colors = ButtonDefaults.textButtonColors(contentColor = if(approveIsCritical) colorResource(R.color.blue)  else Color.Red)
            ) {
                Text(cancelButtonLabel)
            }
            VerticalDivider(
                thickness = 1.dp,
                color = Color.Black,
                modifier = Modifier.height(48.dp)
            )
            TextButton(
                onClick = onApproveClicked,
                colors = ButtonDefaults.textButtonColors(contentColor = if(approveIsCritical) Color.Red else colorResource(R.color.blue))
            ) {
                Text(approveButtonLabel)
            }
        }
    }
}

@Composable
fun SubViewBaseWidget(navController: NavHostController, content: @Composable (ColumnScope.() -> Unit)){
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Row(modifier = Modifier.fillMaxWidth())  {
            Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Arrow Back",
                modifier = Modifier
                    .size(22.dp)
                    .clickable { navController.popBackStack() })
        }
        Spacer(modifier = Modifier.height(10.dp))
        content()
    }
}


@Composable
fun AddNewItemWidget(title: String, onClick: () -> Unit) {
    Surface(
        modifier = Modifier
            .height(45.dp)
            .fillMaxWidth()
            .clickable {onClick.invoke()},
        shape = RoundedCornerShape(5.dp),
        color = colorResource(R.color.shadow_green)
    ) {
        Row(modifier = Modifier.padding(horizontal = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(title, style = MaterialTheme.typography.bodyMedium)
            Icon(
                imageVector = Icons.Rounded.AddCircle,
                contentDescription = null,
                tint = colorResource(R.color.blue)
            )
        }
    }
}