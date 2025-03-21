package com.mgado.expensetracker.views.home.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.mgado.expensetracker.R

@Composable
fun BottomNavigation(currentItem: Int,
                     onItemClick: (Int) -> Unit,) {

    val items = listOf(
        BottomNavItem.Dashboard,
        BottomNavItem.Transactions,
        BottomNavItem.Add,
        BottomNavItem.Statistics,
        BottomNavItem.Calendar)

    Row(modifier = Modifier.fillMaxWidth()
        .background(Color(0xFFEDF2F0)),
        horizontalArrangement = Arrangement.SpaceBetween) {
        items.forEach {
            AddItem(
                screen = it,
                onItemClick = onItemClick,
                selected = it.id == currentItem)
        }
    }
}

@Composable
fun RowScope.AddItem(
    screen: BottomNavItem,
    onItemClick: (Int) -> Unit,
    selected: Boolean) {
    NavigationBarItem(
        label = { Text(text = screen.title,
            style = MaterialTheme.typography.bodySmall,
            maxLines = 1) },
        icon = {
            Icon(
                painterResource(id = screen.icon),
                modifier = Modifier.height(screen.size.dp).width(screen.size.dp),
                contentDescription = screen.title)
        },
        selected = selected,
        alwaysShowLabel = true,
        onClick = { onItemClick(screen.id) },
        colors = NavigationBarItemDefaults.colors(selectedIconColor = screen.activeColor,
            selectedTextColor = screen.activeColor,
            unselectedIconColor = screen.enabledColor,
            indicatorColor = Color.Transparent,
            unselectedTextColor = screen.enabledColor))
}

sealed class BottomNavItem(
    var id: Int,
    var title: String,
    var icon: Int,
    var activeColor: Color,
    var enabledColor: Color,
    var size: Double,
) {
    data object Dashboard :
        BottomNavItem(
            id = 0,
            "Dashboard",
            R.drawable.space_dashboard,
            activeColor = Color.Black,
            enabledColor = Color.Black.copy(alpha = 0.55f),
            size = 28.33)

    data object Transactions :
        BottomNavItem(
            id = 1,
            "Transactions",
            R.drawable.article,
            activeColor = Color.Black,
            enabledColor = Color.Black.copy(alpha = 0.55f),
            size = 28.33)

    data object Add :
        BottomNavItem(
            id = 2,
            "",
            R.drawable.data_saver_on,
            activeColor = Color(0xFF388CDA),
            enabledColor = Color(0xFF388CDA),
            size = 38.33)

    data object Statistics :
        BottomNavItem(
            id = 3,
            "Statistics",
            R.drawable.pie_chart,
            activeColor = Color.Black,
            enabledColor = Color.Black.copy(alpha = 0.55f),
            size = 28.33)

    data object Calendar :
        BottomNavItem(
            id = 4,
            "Calendar",
            R.drawable.calendar_month,
            activeColor = Color.Black,
            enabledColor = Color.Black.copy(alpha = 0.55f),
            size = 28.33)
}