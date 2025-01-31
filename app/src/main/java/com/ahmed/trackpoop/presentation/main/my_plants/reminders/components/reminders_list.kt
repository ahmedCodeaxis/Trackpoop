package com.ahmed.trackpoop.presentation.main.my_plants.reminders.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ahmed.trackpoop.core.data.sampleUserPlant
import com.ahmed.trackpoop.domain.model.ReminderType
import com.ahmed.trackpoop.domain.model.UserPlant
import com.ahmed.trackpoop.presentation.main.my_plants.reminders.ReminderFilter
import com.ahmed.trackpoop.ui.theme.TrackPoopTheme

@Composable
fun RemindersList(
    title: String,
    reminders: List<UserPlant> = emptyList(),
    status: Boolean = true,
    selectedFilter: ReminderFilter = ReminderFilter.ALL,
    onUpdateReminder: (UserPlant, ReminderType) -> Unit = { _, _ -> }
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = title,
            color = Color(0xff000000),
            fontSize = 24.sp,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center,
        )

        reminders.forEach { reminder ->


            if (reminder.hasReminderInWater)
                if ((status && (reminder.isWateredTodayOrOverdue)) ||
                    (!status && reminder.isWateredInFuture)
                ) {
                    ReminderCard(
                        reminder = reminder,
                        type = ReminderType.WATER,
                        onUpdateReminder = onUpdateReminder
                    )
                }

            if (reminder.hasReminderInFertilize)
                if ((status && (reminder.isFertilizedTodayOrOverdue)) ||
                    (!status && reminder.isFertilizedInFuture)
                ) {
                    ReminderCard(
                        reminder = reminder,
                        type = ReminderType.FERTILIZER,
                        onUpdateReminder = onUpdateReminder
                    )
                }
        }
    }
}

@Preview
@Composable
fun RemindersListPreview() {
    TrackPoopTheme {
        RemindersList(
            title = "Today's Reminders",
            reminders = listOf(
                sampleUserPlant,
                sampleUserPlant,
                sampleUserPlant,
            )
        )
    }
}