package com.mgado.expensetracker.views.home.statistics

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.mgado.expensetracker.utils.formatDate
import com.mgado.expensetracker.views.home.widgets.ToggleHeaderWidget
import java.time.LocalDate
import com.mgado.expensetracker.R
import com.mgado.expensetracker.components.CircularIcon
import com.mgado.expensetracker.models.TransactionCategory
import com.mgado.expensetracker.models.dummyCategories
import ir.ehsannarmani.compose_charts.PieChart
import ir.ehsannarmani.compose_charts.models.Pie

@Composable
fun StatisticsView(modifier: Modifier = Modifier, navController: NavHostController) {
    val selectedDateOption = remember {
        mutableStateOf("Monthly")
    }
    val selectedDate = remember {
        mutableIntStateOf(6)
    }
    val categoriesData = listOf(45.0, 30.0, 15.0, 10.0)
    val categoriesColors = dummyCategories.map { colorResource(it.color) }
    var data = remember {
        mutableStateOf(
            0.rangeTo(3).map {
                Pie(label = dummyCategories[it].title,
                    data = categoriesData[it],
                    color = categoriesColors[it].copy(alpha = 0.6f),
                    selectedColor = categoriesColors[it])
            }

        )
    }
    Column(modifier = modifier.padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top) {
        ToggleHeaderWidget(selectedDateOption, navController)
        DateSelector(selectedDateOption = selectedDateOption, selectedDate = selectedDate)
        StatisticsChart(data = data)
        LazyColumn {
            items(count = dummyCategories.size) {
                CategoryRowWidget(dummyCategories[it])
            }
        }
    }
}

@Composable
fun CategoryRowWidget(category: TransactionCategory) {
    var currentProgress by remember { mutableFloatStateOf(0.4f) }
    Row(verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp)) {
        CircularIcon(category.icon)
        Spacer(modifier = Modifier.width(8.dp))
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()) {
                Text(category.title, style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Medium))
                Text("NPR 60,000.00", style = MaterialTheme.typography.labelMedium)
            }
            Spacer(modifier = Modifier.height(8.dp))
            LinearProgressIndicator(modifier = Modifier.height(11.dp).fillMaxWidth(),
                color = colorResource(R.color.green),
                trackColor = colorResource(R.color.gray),
                gapSize = (-12).dp,
                strokeCap = StrokeCap.Round,
                drawStopIndicator = {},
                progress = { currentProgress } )
        }
    }
}

@Composable
fun CategoryIndicator(category: String, color: Color) {
    Row(modifier = Modifier.padding(bottom = 14.dp),verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
        Surface(color = color,
            border = BorderStroke(width = 1.dp,
                color = colorResource(R.color.black)),
            modifier = Modifier.width(25.dp).height(25.dp)) {  }
        Spacer(modifier = Modifier.width(10.dp))
        Text(category, style = MaterialTheme.typography.labelSmall)
    }
}

@Composable
fun DateSelector(selectedDateOption: MutableState<String>, selectedDate: MutableIntState) {
    val now = LocalDate.now()
    val monthsRange = 1.rangeTo(12).map { LocalDate.of(now.year, it, it) }
    val yearsRange = 1.rangeTo(12).map { LocalDate.of(now.year - 6 + it, it, it) }
    val listState = rememberLazyListState()
    LaunchedEffect(true) {
        listState.requestScrollToItem(index = if(selectedDate.intValue > 0) selectedDate.intValue - 1 else selectedDate.intValue)
    }
    LazyRow(modifier = Modifier.padding(vertical = 8.dp, horizontal = 4.dp),
        state = listState) {
        items(count = 12) {
            val date = if(selectedDateOption.value == "Monthly") monthsRange[it] else yearsRange[it]

            Text(formatDate(date, selectedDateOption.value == "Monthly"),
                modifier = Modifier
                    .clickable { selectedDate.intValue = it }
                    .padding(end = 5.dp, start = 5.dp),
                style = MaterialTheme.typography.labelMedium.copy(
                    fontWeight = if (selectedDate.intValue == it) FontWeight.Bold
                    else FontWeight.Normal,
                    color = if (selectedDate.intValue == it) colorResource(R.color.black)
                    else colorResource(R.color.black).copy(alpha = 0.55f)))
        }
    }
}

@Composable
fun StatisticsChart(data: MutableState<List<Pie>>, ) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        PieChart(
            modifier = Modifier.size(150.dp),
            data = data.value,
            onPieClick = {
                val pieIndex = data.value.indexOf(it)
                data.value =
                    data.value.mapIndexed { mapIndex, pie -> pie.copy(selected = pieIndex == mapIndex) }
            },
            selectedScale = 1.2f,
            scaleAnimEnterSpec = spring<Float>(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessLow
            ),
            colorAnimEnterSpec = tween(300),
            colorAnimExitSpec = tween(300),
            scaleAnimExitSpec = tween(300),
            spaceDegreeAnimExitSpec = tween(300),
            style = Pie.Style.Stroke(width = 55.dp),
            selectedPaddingDegree = 2f
        )

        LazyColumn {
            items(count = dummyCategories.size) {
                CategoryIndicator(dummyCategories[it].title, colorResource(dummyCategories[it].color).copy(alpha = 0.6f))
            }
        }
    }
}