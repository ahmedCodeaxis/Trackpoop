package com.ahmed.trackpoop.presentation.poop

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ahmed.trackpoop.domain.model.Poop

@Composable
fun PoopHistoryScreen(
    navController: NavController,
) {
    val poopViewModel = viewModel<PoopViewModel>()
    val context = LocalContext.current
    val state = poopViewModel.state

    LaunchedEffect(Unit) {
        poopViewModel.getUser(context)
        poopViewModel.getPoop(context)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5E1A4))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = "Poop History",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(state.poop.size) { index ->
                    val poop = state.poop[index]
                    PoopRow(
                        record = Poop(
                            color = poop.color,
                            type = poop.type,
                            date = poop.date
                        )
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

@Composable
fun PoopRow(record: Poop) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFD4A373))
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(text = record.date.toString(), fontSize = 16.sp, fontWeight = FontWeight.Medium)
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(text = record.type, fontSize = 16.sp, fontWeight = FontWeight.Medium)
            Text(text = record.color, fontSize = 14.sp, color = Color.Gray)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewPoopHistoryScreen() {
    PoopHistoryScreen(navController = rememberNavController())
}

@Preview(showBackground = true)
@Composable
fun PreviewPoopRow() {
    PoopRow(
        record = Poop(
            color = "Brown",
            type = "Solid",
            date = "2025-01-30"
        )
    )
}
