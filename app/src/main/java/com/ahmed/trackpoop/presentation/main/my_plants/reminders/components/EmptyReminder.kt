package com.ahmed.trackpoop.presentation.main.my_plants.reminders.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
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
fun EmptyReminder() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Image(
            painter = painterResource(id = R.drawable.reminder),
            contentDescription = "Empty list",
            modifier = Modifier
                .size(74.dp)
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "No Reminders Set Yet",
            style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold)
        )
        Spacer(
            modifier = Modifier.height(6.dp)
        )
        Text(
            text = "Add a reminder to keep track of your plants",
            style = TextStyle(fontSize = 16.sp, color = Color.Gray)
        )
//        Button(
//            onClick = { /*TODO*/ },
//            modifier = Modifier.padding(top = 16.dp),
//            colors = ButtonDefaults.buttonColors(
//                containerColor = Color(0xFFE9F3EB)
//            )
//        ) {
//            Text("Add a Reminder", style = TextStyle(color = Color(0xFF30A066), fontWeight = FontWeight.SemiBold))
//        }
    }
}

@Preview
@Composable
fun EmptyReminderPreview() {
    EmptyReminder()
}

