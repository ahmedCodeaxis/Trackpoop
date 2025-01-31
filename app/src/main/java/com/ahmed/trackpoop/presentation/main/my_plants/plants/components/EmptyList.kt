package com.ahmed.trackpoop.presentation.main.my_plants.plants.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ahmed.trackpoop.R

@Composable
fun EmptyList(
    onTabNavigation: (Int) -> Unit = {},
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_plant),
            contentDescription = "Empty list",
            modifier = Modifier
                .width(50.dp)
                .height(50.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "No plants found",
            style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold)
        )
        Spacer(
            modifier = Modifier.height(6.dp)
        )
        Text(
            text = "Add a new plant to get started and maintain it daily",
            style = TextStyle(fontSize = 12.sp, color = Color.Gray)
        )
        Button(
            onClick = { onTabNavigation(1) },
            modifier = Modifier.padding(top = 16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFE9F3EB)
            )
        ) {
            Text("Add a plant", style = TextStyle(color = Color(0xFF30A066)))
        }
    }
}

@Preview
@Composable
fun EmptyListPreview() {
    EmptyList()
}

