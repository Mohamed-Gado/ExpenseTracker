package com.mgado.expensetracker.views.home.transactions

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.mgado.expensetracker.R
import com.mgado.expensetracker.components.CustomDropdownMenu
import com.mgado.expensetracker.components.SectionHeader
import com.mgado.expensetracker.components.TransactionsList
import com.mgado.expensetracker.models.dummyCategories
import com.mgado.expensetracker.models.dummyTransactions
import com.mgado.expensetracker.views.home.widgets.ToggleHeaderWidget
import kotlin.math.ceil

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionsView(modifier: Modifier = Modifier, navController: NavHostController){
    val selectedPage: MutableState<Int> = remember {
        mutableIntStateOf(0)
    }
    val selectedCategory: MutableState<String?> = remember {
        mutableStateOf(null)
    }
    var expandedCategory = remember { mutableStateOf(false) }
    val selectedSortOption: MutableState<String?> = remember {
        mutableStateOf(null)
    }
    val sortOptions: List<String> = listOf("Date", "Price", "Name")
    var expandedSort = remember { mutableStateOf(false) }
    var filteredTransactions = remember(selectedCategory.value) {
        mutableStateOf(dummyTransactions.filter {
            it.category.title == selectedCategory.value || selectedCategory.value == null
        })
    }

    val perPage = 11
    val pagesCount = ceil(filteredTransactions.value.size.toDouble() / perPage).toInt()
    val currentDummyTransactions = remember(selectedPage.value, filteredTransactions.value) {
        val lastPage = selectedPage.value == (pagesCount -1)
        filteredTransactions.value.subList(selectedPage.value * perPage, if (lastPage) filteredTransactions.value.size else (selectedPage.value +1) * perPage )
    }
    val selectedDateOption: MutableState<String> = remember {
        mutableStateOf("Monthly")
    }
    Column(modifier = modifier.padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top) {

        ToggleHeaderWidget(selectedDateOption, navController)
        Spacer(modifier = Modifier.height(16.dp))
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween) {
            SectionHeader(label = "Transactions", icon = R.drawable.receipt_long)
            Row{
                CustomDropdownMenu(
                    modifier = Modifier.width(90.dp),
                    expanded = expandedCategory,
                    placeholder = "Category",
                    selectedItem = selectedCategory,
                    options = dummyCategories.map { it.title }){
                    selectedPage.value = 0
                }
                CustomDropdownMenu(
                    modifier = Modifier.width(70.dp),
                    expanded = expandedSort,
                    placeholder = "Sort by",
                    selectedItem = selectedSortOption,
                    options = sortOptions){
                    if(it == "Price"){
                        filteredTransactions.value = filteredTransactions.value.sortedBy { it.price }
                    } else {
                        filteredTransactions.value = filteredTransactions.value.sortedBy { it.date }
                    }
                }
                Spacer(modifier = Modifier.width(16.dp))
            }
        }
        TransactionsList(transactions = currentDummyTransactions)
        Spacer(Modifier.height(16.dp))
        PaginationWidget(pagesCount, selectedPage)
    }
}

@Composable
fun PaginationWidget(pagesCount: Int, selectedPage: MutableState<Int>) {
    Surface(
        color = colorResource(R.color.light_gray),
        shape = RoundedCornerShape(5.dp),
    ) {
        LazyRow(
            modifier = Modifier
                .height(31.dp)
                .padding(horizontal = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            items(count = pagesCount) {
                Row {
                    Spacer(Modifier.width(2.dp))
                    Card(
                        modifier = Modifier
                            .width(21.dp)
                            .height(21.dp)
                            .clickable {
                                selectedPage.value = it
                            },
                        shape = RoundedCornerShape(5.dp),
                        colors = CardDefaults.cardColors(containerColor = colorResource(R.color.shadow_gray))
                    ) {
                        Text(
                            "${it + 1}",
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(top = 2.dp),
                            style = MaterialTheme.typography.labelSmall,
                            textAlign = TextAlign.Center
                        )
                    }
                    Spacer(Modifier.width(2.dp))
                }
            }
        }
    }
}

@Composable
fun ToggleButton(selectedOption: MutableState<String>, firstValue: String, secondValue: String
) {
    Box(contentAlignment = Alignment.CenterStart) {
        Surface(
            modifier = Modifier
                .height(34.dp)
                .width(239.dp),
            color = colorResource(R.color.background_green),
            shape = RoundedCornerShape(5.dp)
        ) {}
        Surface(
            modifier = Modifier
                .width(134.dp)
                .height(34.dp)
                .clickable {
                    selectedOption.value = firstValue
                },
            shape = RoundedCornerShape(5.dp),
            color = if (selectedOption.value == firstValue) colorResource(R.color.gray) else Color.Transparent
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(firstValue)
            }
        }
        Surface(
            modifier = Modifier
                .width(134.dp)
                .height(34.dp)
                .align(Alignment.CenterEnd)
                .clickable {
                    selectedOption.value = secondValue
                },
            shape = RoundedCornerShape(5.dp),
            color = if (selectedOption.value == secondValue) colorResource(R.color.gray) else Color.Transparent
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(secondValue)
            }
        }
    }
}
