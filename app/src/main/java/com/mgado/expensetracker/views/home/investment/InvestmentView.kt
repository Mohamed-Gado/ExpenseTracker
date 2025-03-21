package com.mgado.expensetracker.views.home.investment

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.mgado.expensetracker.R
import com.mgado.expensetracker.components.SectionHeader
import com.mgado.expensetracker.components.SubViewBaseWidget


@Composable
fun InvestmentView(navController: NavHostController) {
    SubViewBaseWidget(navController = navController) {
        SectionHeader(label = "Smart Investment",
            icon = R.drawable.currency_exchange,
            modifier = Modifier
                .padding(vertical = 8.dp)
                .align(Alignment.Start))

        Column(modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)) {
            SectionHeader(label = "Your saved resources",
                labelStyle = MaterialTheme.typography.titleSmall,
                icon = R.drawable.bookmark,
                iconSize = 16,
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .align(Alignment.Start))
            SavedResources()
            SectionHeader(label = "Read articles",
                labelStyle = MaterialTheme.typography.titleSmall,
                icon = R.drawable.newsmode,
                iconSize = 16,
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .align(Alignment.Start))
            ResourcesList(resourceIcon = R.drawable.article)
            SectionHeader(label = "Watch Videos",
                labelStyle = MaterialTheme.typography.titleSmall,
                icon = R.drawable.youtube_activity,
                iconSize = 16,
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .align(Alignment.Start))
            ResourcesList(resourceIcon = R.drawable.play_arrow)
            SectionHeader(label = "Listen to Podcasts",
                labelStyle = MaterialTheme.typography.titleSmall,
                icon = R.drawable.podcasts,
                iconSize = 16,
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .align(Alignment.Start))
            ResourcesList(resourceIcon = R.drawable.podcasts)
        }
    }
}

@Composable
fun ResourcesList(resourceIcon: Int) {
    Surface(color = colorResource(R.color.background_green),
        shape = RoundedCornerShape(5.dp),
        modifier = Modifier.height(116.dp)
    ) {
        LazyRow(modifier = Modifier.padding(horizontal = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            items(4) {
                Surface(shape = RoundedCornerShape(topStart = 5.dp, bottomStart = 5.dp),
                    color = colorResource(R.color.gray),
                    modifier = Modifier
                        .width(125.dp)
                        .height(90.dp)
                ) {
                    Column(verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            painter = painterResource(resourceIcon),
                            contentDescription = null,
                            modifier = Modifier
                                .height(16.dp)
                                .width(16.dp),
                            tint = colorResource(R.color.black).copy(alpha = 0.5f)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SavedResources() {
    Box(contentAlignment = Alignment.BottomEnd) {
        Surface(color = colorResource(R.color.shadow_green),
            shape = RoundedCornerShape(5.dp),
            modifier = Modifier.height(168.dp)
        ) {
            LazyRow(modifier = Modifier.padding(horizontal = 6.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                items(4) {
                    Surface(shape = RoundedCornerShape(5.dp),
                        color = colorResource(R.color.white),
                        modifier = Modifier
                            .width(123.dp)
                            .height(137.dp)
                    ) {}
                }
            }
        }
        IconButton(onClick = {}) {
            Icon(imageVector = Icons.Rounded.AddCircle,
                contentDescription = null,
                modifier = Modifier
                    .height(35.dp)
                    .width(35.dp),
                tint = colorResource(R.color.blue))
        }
    }
}